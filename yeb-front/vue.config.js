const { defineConfig } = require("@vue/cli-service");

let proxyObj = {};

proxyObj["/"] = {
    // websocket
    ws: false,
    // 目标地址
    target: "http://localhost:8081",
    // 发送请求头 host 会被改成 target
    changeOrigin: true,
    //不重写请求地址
    pathRewrite: {
        "^/": "/",
    },
};

// 在线聊天 代理
proxyObj["/ws"] = {
    ws: true,
    target: "ws://localhost:8081",
    // 发送请求头 host 会被改成 target
    changeOrigin: true,
    //不重写请求地址
    pathRewrite: {
        "^/ws": "/",
    },
};

module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        host: "localhost",
        port: 8080,
        proxy: proxyObj,
    },
    lintOnSave: false,
    publicPath: "./",
});
