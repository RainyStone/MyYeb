import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "font-awesome/css/font-awesome.css";

import { postRequest } from "./utils/api";
import { putRequest } from "./utils/api";
import { getRequest } from "./utils/api";
import { deleteRequest } from "./utils/api";
import { downloadRequest } from "./utils/download";

Vue.config.productionTip = false;
Vue.use(ElementUI, { size: "small" });
//插件形式使用请求方法
Vue.prototype.postRequest = postRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.downloadRequest = downloadRequest;

import { initMenu } from "./utils/menus";

//注册路由导航守卫，每次路由跳转前执行，并初始化菜单信息
router.beforeEach((to, from, next) => {
    //用户是否登录
    if (window.sessionStorage.getItem("tokenStr")) {
        initMenu(router, store); //初始化菜单信息
        // 判断用户信息是否存在
        if (!window.sessionStorage.getItem("user")) {
            return getRequest("/admin/info").then((resp) => {
                if (resp) {
                    // 存入用户信息
                    window.sessionStorage.setItem("user", JSON.stringify(resp));
                    next();
                }
            });
        }
        next();
    } else {
        //未登录，跳回登录页
        if (to.path == "/") {
            next();
        } else {
            next("/?redirect=" + to.path);
        }
    }
});

new Vue({
    router,
    store,
    render: (h) => h(App),
}).$mount("#app");
