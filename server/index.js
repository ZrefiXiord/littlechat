const express = require("express");
const app = express();
const http = require("http");
const server = http.createServer(app);
const { Server } = require("socket.io");
const io = new Server(server);

let clientsName = {};
io.on("connection", (socket) => {
  clientsName[socket.client.id] = "Someone";
  socket.broadcast.emit("join");

  socket.on("message", (msg) => {
    socket.broadcast.emit("message", msg, clientsName[socket.client.id]);
  });


  socket.on("nickname", (newClientName) => {
    clientsName[socket.client.id] = newClientName;
  });

  socket.on("disconnect", (reason) => {
    console.log(`user disconnected for : ${reason}`);
    socket.broadcast.emit("disconnection", `${clientsName[socket.client.id]}`);
    delete clientsName[socket.client.id];
  });
});

server.listen(3000, () => {
  console.log("listening on *:3000");
});
