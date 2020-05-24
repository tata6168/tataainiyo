function dataTransformUrl(url,data){
    let urls = url+"?";
    if(data instanceof Object){
        for(let key in data){
            if(!(data[key] == null||data[key] == ""))
                urls = urls+key+"="+data[key]+"&";
        }
    }
    return urls;
}

function dataSubmit(url,data){
    axios.post(url,data).then(data=>{
        this.$notify.success({
            title: 'Info',
            message: '这是一条没有关闭按钮的消息',
            showClose: false
        });
    }).catch();
}

function notify(){
    this.$notify.success({
        title: 'Info',
        message: '这是一条没有关闭按钮的消息',
        showClose: false
    });
}
Vue.component('button-counter', {
    data: function () {
        return {
            count: 0
        }
    },
    template: '<button v-on:click="count++">You clicked me {{ count }} times.</button>'
})