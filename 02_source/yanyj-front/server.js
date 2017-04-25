import express from 'express';

import path from 'path';
let events = require("events");

require('babel-register');
let emitter = new events.EventEmitter()

let app = express();

app.use(express.static(path.join(__dirname, '/')));

app.listen(8888, (error) => {
	if (error) {
		console.log('服务启动失败！请查看' + error.stack);
	}
	console.log('服务启动成功！\n请访问： http://localhost:8888......');
});



