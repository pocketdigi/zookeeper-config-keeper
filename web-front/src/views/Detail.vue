<template>
    <div>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{application}}</el-breadcrumb-item>
            <el-breadcrumb-item>{{profile}}</el-breadcrumb-item>
        </el-breadcrumb>
        <div class="hint">只支持properties格式，所有注释在保存后会被丢弃。受限于Spring cloud zookeeper
            config存储特性，读取配置时无法按原来存储的顺序显示。
        </div>
        <div class="codemirror" style="text-align: left;">
            <!-- codemirror -->
            <codemirror v-model="code"
                        :options="cmOption"
                        >
            </codemirror>
        </div>
        <div id="button">
            <el-button type="primary" round @click="dialogVisible = true">保存配置</el-button>
            <el-button type="danger" round @click="deleteDialogVisible = true">删除配置</el-button>
        </div>


        <el-dialog
            title="提示"
            :visible.sync="dialogVisible"
            width="20%">
            <span>确定保存配置？部分配置保存后您可能需要重启应用才能生效</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitForm">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog
            title="提示"
            :visible.sync="deleteDialogVisible"
            width="20%">
            <span>确定删除配置？删除后不可恢复！！！</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="deleteDialogVisible = false">取 消</el-button>
                <el-button type="danger" @click="deleteConfig" >确定删除</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import EventBus from '@/event-bus'

    // language
    import 'codemirror/mode/properties/properties.js'
    // theme css
    import 'codemirror/theme/monokai.css'
    // active-line.js
    import 'codemirror/addon/selection/active-line.js'
    // styleSelectedText
    import 'codemirror/addon/selection/mark-selection.js'
    import 'codemirror/addon/search/searchcursor.js'
    // highlightSelectionMatches
    import 'codemirror/addon/scroll/annotatescrollbar.js'
    import 'codemirror/addon/search/matchesonscrollbar.js'
    import 'codemirror/addon/search/searchcursor.js'
    import 'codemirror/addon/search/match-highlighter.js'

    export default {
        name: "Detail",
        data() {
            return {
                path: '',
                application: '',
                profile: '',
                code: 'sfsf=fe',
                cmOption: {
                    tabSize: 4,
                    foldGutter: false,
                    styleActiveLine: true,
                    lineNumbers: true,
                    line: true,
                    mode: 'text/x-properties',
                    theme: 'monokai',
                },
                dialogVisible: false,
                deleteDialogVisible: false
            }
        },
        mounted() {
            this.loadData();
        },
        methods: {
            loadData() {
                this.path = this.$route.params.path;
                let split = this.path.split(',');
                if (split.length === 2) {
                    this.application = split[0];
                    this.profile = split[1];
                } else {
                    this.application = this.path;
                    this.profile = 'default';
                }
                let _this = this;
                _this._get('/config/' + _this.application + '/' + this.profile, response => {
                    _this.code = response.data.data;
                });

            },
            submitForm() {
                this.dialogVisible=false;
                let _this = this;
                _this._post('/config',
                    {application: _this.application,profile: _this.profile,configData: _this.code}, response => {
                        console.log(response.data);
                        _this.$notify({
                            title: '成功',
                            message: '配置保存成功',
                            type: 'success'
                        });
                        _this.loadData();
                    });
            },
            deleteConfig() {
                this.deleteDialogVisible=false;
                let _this = this;
                _this._delete('/config/'+_this.application+'/'+_this.profile,()=>{
                    EventBus.$emit('treeUpdated');
                    _this.$router.push('/');
                    this.$notify({
                        title: '成功',
                        message: '删除成功',
                        type: 'success'
                    });

                });

            }
        },
        watch: {
            $route() {
                this.loadData();
            }
        }
    }
</script>

<style>
    #button {
        margin-top: 20px;
    }
    .hint {
        text-align: left;
        margin-top: 10px;
        font-size: 12px;
    }

    .CodeMirror {
        height: 800px;
    }

    .codemirror {
        margin-top: 20px;
    }
</style>