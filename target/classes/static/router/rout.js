//同步请求，获得权限数据，再放入到路由

import test from "./test";

function AwaitRequest() {
    var permission;
    var ajax = new XMLHttpRequest();
    ajax.open("GET","/permission/selectAll",false);
    ajax.onreadystatechange = function(){
        if(ajax.readyState==4){
            if(ajax.status==200||ajax.status==304){
                permission = ajax.responseText;
            }
        }
    };
    ajax.send();
    return permission;
};

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => console.debug(err))
};


var xxrouter = new VueRouter({
    routes:routes
});