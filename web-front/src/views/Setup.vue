<template>
    <el-card class="box-card" style="margin: 80px auto auto auto">
        <div id="header">设置账号密码</div>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="pass">
                <el-input v-model="form.pass" show-password></el-input>
            </el-form-item>
            <el-form-item label="重复密码" prop="checkPass">
                <el-input v-model="form.checkPass" show-password></el-input>
            </el-form-item>
            <el-button type="primary" @click="submitForm('form')">保存</el-button>
        </el-form>
    </el-card>


</template>
<script>
    export default {
        data() {
            let validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.form.checkPass !== '') {
                        this.$refs.form.validateField('checkPass');
                    }
                    callback();
                }
            };
            let validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.form.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };

            return {
                form: {
                    username: '',
                    pass: '',
                    checkPass: ''
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 2, max: 10, message: '长度在2-10字符', trigger: 'blur'}
                    ],
                    pass: [
                        {validator: validatePass, trigger: 'blur'},
                        {min: 5, max: 20, message: '长度在5-20字符', trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: validatePass2, trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
            submitForm(formName) {
                let _this = this;
                this.$refs[formName].validate((valid) => {
                    console.log('submit');
                    if (valid) {
                        _this._post('/users',
                            {username: _this.form.username, password: _this.form.pass},() => {
                                _this.$router.push('/login');
                            });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
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
