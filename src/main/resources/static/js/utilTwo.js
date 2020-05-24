Vue.component('button-counter2', {
    data: function () {
        return {
            value1: 0,
            value2:100,
        }
    },
    template: ' <div>' +
        '<el-slider v-model="value1"></el-slider> ' +
        '<el-slider v-model="value2"></el-slider>' +
        '</div>'
})