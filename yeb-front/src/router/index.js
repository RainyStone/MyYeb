import Vue from "vue";
import VueRouter from "vue-router";
import Login from "../views/Login";
import Home from "../views/Home.vue";
import FriendChat from "../views/chat/FriendChat.vue";
import AdminInfo from "../views/AdminInfo.vue";

Vue.use(VueRouter);

//默认页面登录页，其它路由信息是查数据库的menu表动态加载的
const routes = [
    {
        path: "/",
        name: "Login",
        component: Login,
        hidden: true,
    },
    {
        path: "/home",
        name: "Home",
        component: Home,
        children: [
            {
                path: "/chat",
                name: "在线聊天",
                component: FriendChat,
            },
            {
                path: "/userinfo",
                name: "个人中心",
                component: AdminInfo,
            },
        ],
    },
];

const router = new VueRouter({
    routes,
});

export default router;
