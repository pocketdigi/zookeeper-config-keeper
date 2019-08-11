<template>
    <el-card class="box-card" style="margin: 80px auto auto auto">
        <div id="header">Login</div>
        <el-form ref="form" :model="form" label-width="80px">
            <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="form.password" show-password></el-input>
            </el-form-item>
            <el-button type="primary" @click="submitForm('form')">登录</el-button>
        </el-form>
    </el-card>


</template>
<script>
    export default {
        data() {
            return {
                form: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 2, max: 10, message: '长度在2-10字符', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                    ]
                }
            }
        },
        methods: {
            submitForm(formName) {
                let _this = this;
                this.$refs[formName].validate((valid) => {
                    console.log('submit');
                    if (valid) {
                        _this._post('/users/login',
                            _this.form, response => {
                                let token = response.data.data;
                                console.log(token);
                                sessionStorage.headers = JSON.stringify(
                                    {'authorization': 'Bearer ' + token});
                                _this.$router.push('/');

                            });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
        },
        mounted() {
            let _this = this;
            this._get('/users/setup_check', response => {
                if(!response.data.data) {
                    //未初始化
                    _this.$router.push('/setup');
                }
            });
        }

    }
</script>

<style scoped>
    #header {
        font-size: 30px;
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .text {
        font-size: 14px;
    }

    .item {
        padding: 18px 0;
    }

    .box-card {
        width: 480px;
    }
</style>
