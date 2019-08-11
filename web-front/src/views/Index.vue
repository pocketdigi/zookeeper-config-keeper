<template>
    <div>
        <div>
            <div style="margin-top: 20px">感谢使用ZooKeeper Config Keeper,请点击下面的按钮创建配置。</div>
            <el-button type="primary" round style="margin-top: 20px"
                       @click="dialogFormVisible = true">创建新的配置
            </el-button>
        </div>

        <el-dialog title="新的配置" :visible.sync="dialogFormVisible" width="30%">
            <el-form :model="form" >
                <el-form-item label="Application">
                    <el-input v-model="form.application" autocomplete="off" class="input"></el-input>
                </el-form-item>
                <el-form-item label="Spring Profile">
                    <el-input v-model="form.profile" autocomplete="off" class="input"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="createNew">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import EventBus from '@/event-bus'

    export default {
        name: "Index",
        data() {
            return {
                dialogFormVisible: false,
                form: {
                    application:'',
                    profile:''
                },
            }
        },
        methods: {
            createNew() {
                this.dialogFormVisible = false;
                let _this=this;
                _this._post('/config',
                    _this.form, () => {
                        if(_this.form.profile) {
                            _this.$router.push('/detail/'+_this.form.application+','+_this.form.profile);
                        }else{
                            _this.$router.push('/detail/'+_this.form.application);
                        }
                        EventBus.$emit('treeUpdated');
                    });
            }

        }
    }
</script>

<style scoped>
    .input {
        width: 320px;
    }
</style>