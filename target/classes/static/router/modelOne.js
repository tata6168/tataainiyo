var modelOne = {
    template: '<div><el-button type="text" @click="dialogVisible = true">点击打开 Dialog</el-button>\n' +
        '\n' +
        '<el-dialog\n' +
        '  title="提示"\n' +
        '  :visible.sync="dialogVisible"\n' +
        '  width="30%"\n' +
        '  modal\n'+
        '  :before-close="handleClose">\n' +
        '  <span>这是一段信息</span>\n' +
        '  <span slot="footer" class="dialog-footer">\n' +
        '    <el-button @click="dialogVisible = false">取 消</el-button>\n' +
        '    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>\n' +
        '  </span>\n' +
        '</el-dialog></div>',
    data(){
        return {
            dialogVisible: false
        }
    },
    methods:{
        handleClose() {
            this.dialogVisible=false;
        }
    }

}