let query_str = window.location.search.replace("?", "");
if (query_str.length === 0) {
    window.location.search = "?page=dashboard"
}
let nav_items = document.getElementsByTagName("mdui-navigation-drawer-item");
let nav_bar = document.getElementsByTagName("mdui-navigation-drawer")[0];
for (let i = 0; i < nav_items.length; i++) {
    let item = nav_items[i];
    item.addEventListener("focus", (event) => {
        window.location.search = "?page=" + event.target.value;
    })
}
let queriesMap = {};
query_str.split("&").forEach((item) => {
    let query_k_v = item.split("=");
    queriesMap[query_k_v[0]] = query_k_v[1];
});
let pages = [
    "dashboard",
    "boxes",
    "users",
    "manager_settings"
]
let page_name_map = {
    "dashboard": "仪表盘",
    "boxes": "实例",
    "users": "用户",
    "manager_settings": "面板设置"
}
let page_name = queriesMap['page'];

document.getElementById("nav_drawer_opener").addEventListener("click", (e) => {
    document.getElementById("nav_drawer").open = true;
})

nav_bar.setAttribute("value", page_name);
let title = document.getElementsByTagName("title")[0];
title.innerText = page_name_map[page_name] + title.innerText;
pages.forEach((item) => {
    if (item === page_name) {
        document.getElementById(item).style.display = "flex"
    } else {
        document.getElementById(item).style.display = "none";
    }
})

let socket_url = "ws://" + window.location.hostname + ":" + window.location.port + "/socket";
let socket_obj = new WebSocket(socket_url);
socket_obj.onopen = function () {
    socket_obj.send('connected');
}
socket_obj.onmessage = function (e) {
    let resp = JSON.parse(e.data);
    switch (resp['type']) {
        case "device_status": {
            let cpu_status = resp['cpu_status'];

            let cpu_total = cpu_status['total'];
            let cpu_total_element = document.getElementById("cpu_total");
            cpu_total_element.innerText = "CPU总核数：" + cpu_total;

            let cpu_acaliable = cpu_status['acaliable'];
            let cpu_acaliable_element = document.getElementById("cpu_acaliable");
            cpu_acaliable_element.innerText = cpu_acaliable;
            document.getElementById("cpu_status_track").setAttribute("value", Number.parseFloat(cpu_acaliable.replace("%", "")) / 100);


            let memory_status = resp['memory_status'];

            let memory_total = memory_status['total'];
            let memory_total_element = document.getElementById("memory_total");
            memory_total_element.innerText = "内存总空间：" + memory_total;

            let memory_acaliable = memory_status['acaliable'];
            let memory_acaliable_element = document.getElementById("memory_acaliable");
            memory_acaliable_element.innerText = memory_acaliable;
            document.getElementById("memory_status_track").setAttribute("value", Number.parseFloat(memory_acaliable.replace("%", "")) / 100);
        }
    }
}