if ("MozWebSocket" in window) {
	WebSocket = MozWebSocket;
}

var ola = {
	version : '2.0.1',
	ws: undefined,
	uuid: "73836387-0000-0000-0000-0000-0000000000",
	connected: false,
	socket_connected: false,
	onConnect: undefined,
	onMessage: undefined,
	onClose: undefined,
	_connectSuccess: undefined,
	_connectError: undefined,
	_username: undefined,
	_password: undefined,
	_extn: undefined,

	connect: function(url, username, password, success, error) {
		if (!url) url = "ws://" + document.location.hostname + ':' + document.location.port + "/ola_socket";

		if ("WebSocket" in window) {
			// browser supports websockets
			this.ws = new WebSocket(url);
			if (this.ws) {
				this.ws.onclose = ola.onClose;
				this.ws.onopen = ola._onOpen;
				this.ws.onmessage = ola._onMessage;
				this._connectSuccess = success;
				this._connectError = error;
				this._username = username;
				this._password = password;
			}
		} else {
			// browser does not support websockets
			if ("console" in window) {
				console.log("you don't have websocket");
			}
			return false;
		}

		return this;
	},

	close: function() {
		ola.ws.close();
	},

	_onOpen: function() {
		ola.auth(ola._username, ola._password);
	},

	_onMessage: function (evt) {
		console.log(evt.data);

		var msg = JSON.parse(evt.data);
		if (msg.event_name == "command/reply" && msg.code == 200) {
			ola.ws.onmessage = this.onMessage;
		}
		ola.ws.onmessage = ola.onMessage;
		ola.onConnect();
	},

	auth: function(username, password) {
		return ola.send({cmd: "auth", args:{username:username, password:password, accept:"application/json"}});
	},

	get_agent_state: function() {
		return this.send({action:"api", cmd:"get_agent_state", args:{extn: this._extn}});
	},

	get_trunk_state: function() {
		return this.send({action:"api", cmd:"get_trunk_state"});
	},

	login : function(queue, extn, params) {
		var args = {queue:queue, extn: extn};
		this.merge(args, params);
		this._extn = extn;
		return ola.send({action:"api", cmd:"login", args: args });
	},

	logout: function() {
		return this.send({action:"api", cmd:"logout", args:{extn: this._extn}});
	},

	ping: function() {
		return ola.send({cmd: "ping"})
	},

	subscribe: function(k) {
		return ola.send({cmd:"subscribe", args:{key:k}});
	},

	unsubscribe: function(k) {
		return ola.send({cmd:"unsubscribe", args:{key:k}});
	},

	go_ready: function() {
		return this.send({action:"api", cmd:"go_ready", args:{extn: this._extn}});
	},
	
	go_ready2: function(extn) {
		return this.send({action:"api", cmd:"go_ready", args:{extn: extn}});
	},
	
	go_break: function(reason) {
		return this.send({action:"api", cmd:"go_break", args:{extn: this._extn, reason: reason}});
	},
	
	go_break2: function(extn) {
		return this.send({action:"api", cmd:"go_break", args:{extn: extn, reason: ""}});
	},

	toggle_ready : function() {
		return this.send({action:"api", cmd:"toggle_ready", args:{extn: this._extn}});
	},

	answer : function() {
		return this.send({action:"api", cmd:"answer", args:{extn: this._extn}});
	},

	hangup : function() {
		return this.send({action:"api", cmd:"hangup_other", args:{extn: this._extn}});
	},

	dial : function(dst,otherStr,gateway) {
		return this.send({action:"api", cmd:"dial", args:{extn: this._extn, dest: dst, gateway: gateway,otherStr:otherStr}});
	},

	transfer : function(dst, src) {
		var extn = src;

		if (extn == null || typeof(extn) == "undefined") {
			extn = this._extn;
		}

		return this.send({action:"api", cmd:"transfer", args:{extn:extn, dest:dst}});
	},

	transfer_uuid : function(uuid, dst) {
		return this.send({action:"api", cmd:"transfer", args:{channel_uuid:uuid, dest:dst}});
	},

	monitor : function(dst, src) {
		var extn = src;
		if (extn == null || typeof(extn) == "undefined") {
			extn = this._extn;
		}

		return this.send({action:"api", cmd:"monitor", args:{extn: extn, dest: dst}});
	},

	monitor_uuid: function(uuid) {
		return this.send({action:"api", cmd:"monitor", args:{extn: this._extn, channel_uuid: uuid}});
	},

	intercept: function(dst, src) {
		var extn = src;

		if (extn == null || typeof(extn) == "undefined") {
			extn = this._extn;
		}

		return this.send({action:"api", cmd:"intercept", args:{extn: extn, dest: dst}});
	},

	intercept_uuid: function(uuid) {
		return this.send({action:"api", cmd:"intercept", args:{extn: this._extn, channel_uuid: uuid}});
	},

	three_way : function(dst, src) {
		var extn = src;

		if (extn == null || typeof(extn) == "undefined") {
			extn = this._extn;
		}

		return this.send({action:"api", cmd:"monitor", args:{extn: extn, dest: dst, three_way:"true"}});
	},

	three_way_uuid : function(uuid) {
		return this.send({action:"api", cmd:"monitor", args:{extn: this._extn, channel_uuid: uuid, three_way:"true"}});
	},

	unmonitor : function(ext) {
		return this.send({action:"api", cmd:"unmonitor", args:{extn: ext}});
	},

	whisper : function(w) {
		// who = "agent" / "caller" / "both" / "none"
		return this.send({action:"api", cmd:"whisper", args:{extn: this._extn, who: w}});
	},

	consult : function(dst) {
		return this.send({action:"api", cmd:"consult", args:{extn: this._extn, dest: dst}});
	},

	hold : function() {
		return this.send({action:"api", cmd:"hold", args:{extn: this._extn}});
	},

	unhold : function(dst) {
		return this.send({action:"api", cmd:"unhold", args:{extn: this._extn}});
	},

	toggle_hold : function() {
		return this.send({action:"api", cmd:"toggle_hold", args:{extn: this._extn}});
	},

	take_call : function(channel_uuid) {
		return this.send({action:"api", cmd:"take_call", args:{extn: this._extn, channel_uuid:channel_uuid}});
	},

	conference : function(dst) {
		return this.send({action:"api", cmd:"conference", args:{extn: this._extn, dest: dst}});
	},

	conference_uuid : function(uuid) {
		return this.send({action:"api", cmd:"conference", args:{extn: this._extn, channel_uuid: uuid}});
	},

	broadcast : function(numbers, mute) {
		return this.send({action:"api", cmd:"broadcast", args:{extn: this._extn, numbers: numbers, mute:mute}});
	},

	/* send chat to a queue or an agent
	  to = queue             send to queue
	  to = queue.agent       send to agent
	*/
	chat: function (to, message, content_type) {
		return this.send({action:"api", cmd:"chat", args:{to:to, message:message, content_type:content_type}});
	},

	message: function (from, to, message, content_type) {
		return this.send({action:"api", cmd:"message", args:{from:from, to:to, message:message, content_type:content_type}});
	},

	alarm: function (queue, state) {
		return this.send({action:"api", cmd:"alarm", args:{queue:queue, state:state}});
	},

	/* dispatching apis */

	dlogin: function (ext) {
		return this.send({action:"api", cmd:"dlogin", args:{extn: ext}});
	},

	dlogout: function (ext) {
		return this.send({action:"api", cmd:"dlogout", args:{extn: ext}});
	},

	inject: function (ext, uuid) {
		return this.send({action:"api", cmd:"inject", args:{extn: ext, channel_uuid: uuid}});
	},

	kill: function(uuid, cause) {
		return this.send({action:"api", cmd:"kill", args:{channel_uuid: uuid, cause: cause}});
	},

	eavesdrop: function(uuid) {
		return this.send({action:"api", cmd:"eavesdrop", args:{channel_uuid: uuid}});
	},

	conf: function(name, action, member) {
		return this.send({action:"api", cmd:"conf", args:{name: name, action:action, member:member}});
	},

	answer_all: function(ext, queue) {
		return this.send({action:"api", cmd:"answer_all", args:{extn: ext, queue:queue}});
	},

	group_call: function(ext, queue, numbers, batch_accept) {
		return this.send({action:"api", cmd:"group_call", args:{extn: ext, queue:queue, numbers:numbers, batch_accept:batch_accept}});
	},

	sip_gateway: function (profile, gateway, op) {
		return this.send({action:"api", cmd:"sip_gateway", args:{profile:profile, gateway:gateway, op:op}});
	},

	play: function (conference, filename) {
		return this.send({action:"api", cmd:"play", args:{conference:conference, filename:filename}});
	},

	stop_play: function (conference, filename) {
		return this.send({action:"api", cmd:"play", args:{conference:conference, filename:filename}});
	},

	merge_call: function(ext1, ext2) {
		return this.send({action:"api", cmd:"merge_call", args:{extn1:ext1, extn2:ext2}});
	},

	/* common apis*/

	/*phone control api, only yealink support for now*/
	api_handfree: function (ext) {
		return this.send({action:"api", cmd:"api_handfree", args:{extn: ext}});
	},

	send : function(msg) {
		msg.uuid = ola.next_uuid();
		log(JSON.stringify(msg));
		ola.ws.send(JSON.stringify(msg));
		return msg.uuid;
	},

	merge: function(target, additional){
		for (var i in additional) {
			if (additional.hasOwnProperty(i)) {
				target[i] = additional[i];
			}
		}
	},

	next_uuid : function() {
		var u = (parseFloat(ola.uuid.substring(29)) + 1).toString();
		u = u == "2147483647" ? "0" : u;
		while(u.length < 12) { u = "0" + u;	}
		ola.uuid = "73836387-0000-0000-0000-0000-" + u;
		return ola.uuid;
	}
};

if ('jQuery' in this) jQuery.ola = this.ola;
