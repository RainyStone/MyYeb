import router from "@/router";
import { getRequest } from "./api";
import store from "@/store";

export const initMenu = (router, store) => {
    //已经有路由信息，则无需再请求后端获取
    if (store.state.routes.length > 0) {
        return;
    }
    getRequest("/system/cfg/menu").then((data) => {
        if (data) {
            // 格式化好的 Router
            let fmtRoutes = formatRoutes(data);
            // 添加到vue router
            router.addRoutes(fmtRoutes);
            // 路由数据存到 vuex
            store.commit("initRoutes", fmtRoutes);
            // 连接 WebSocket
            store.dispatch("connect");
        }
    });
};

// 这里的参数routes是后端返回的菜单信息
export const formatRoutes = (routes) => {
    let fmtRoutes = [];
    routes.forEach((router) => {
        let { path, component, name, iconCls, children } = router;
        if (children && children instanceof Array) {
            // 递归格式化子路由
            children = formatRoutes(children);
        }

        let fmRouter = {
            path: path,
            name: name,
            iconCls: iconCls,
            children: children,
            //根据组件名字导入组件对象
            component(resolve) {
                if (component.startsWith("Home")) {
                    require(["../views/" + component + ".vue"], resolve);
                } else if (component.startsWith("Emp")) {
                    require(["../views/emp/" + component + ".vue"], resolve);
                } else if (component.startsWith("Per")) {
                    require(["../views/per/" + component + ".vue"], resolve);
                } else if (component.startsWith("Sal")) {
                    require(["../views/sal/" + component + ".vue"], resolve);
                } else if (component.startsWith("Sta")) {
                    require(["../views/sta/" + component + ".vue"], resolve);
                } else if (component.startsWith("Sys")) {
                    require(["../views/sys/" + component + ".vue"], resolve);
                }
            },
        };
        fmtRoutes.push(fmRouter);
    });
    return fmtRoutes;
};
