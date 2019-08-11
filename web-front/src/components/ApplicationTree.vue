<template>
    <div>
        <div>应用配置管理</div>
        <el-input
            placeholder="输入应用名搜索"
            v-model="filterText" style="margin-top: 10px">
        </el-input>

        <el-tree
            class="filter-tree"
            :data="data"
            :props="defaultProps"
            default-expand-all
            :highlight-current="true"
            :filter-node-method="filterNode"
            @node-click="nodeClick"
            ref="tree">
        </el-tree>
    </div>
</template>

<script>
    import EventBus from '@/event-bus'

    export default {
        name: "ApplicationTree.vue",
        watch: {
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },

        methods: {
            filterNode(value, data) {
                if (!value) {
                    return true;
                }
                return data.application.indexOf(value) !== -1;
            },
            loadTree() {
                let _this = this;
                _this._get('/config/tree', response => {
                    _this.data = response.data.data;
                });

            },
            nodeClick(data) {
                if(data.children) {
                    //不处理父节点
                    return;
                }
                let path=data.path;
                this.$router.push('/detail/'+path);
            }
        },

        data() {
            return {
                filterText: '',
                data: [],
                defaultProps: {
                    children: 'children',
                    label: 'label'
                }
            };
        },
        mounted() {
            let _this=this;
            this.loadTree();
            EventBus.$on('treeUpdated', () => {
                _this.loadTree();
            });
        },
        destroyed() {
            EventBus.$off('treeUpdated');
        }
    }
</script>

<style scoped>

</style>