import axios from 'axios';
import Constants from './constants/constants'

export default {
    install: function(Vue,) {
        Vue.prototype._post= function (path,data,callback) {
            let _this=this;

            let address=Constants.BASE_URL;

            axios
                .post(address + path, data,getConfig())
                .then(response => {
                    if(response.data.error !== 0) {
                        let error=new Error();
                        error.response=response;
                        throw error;
                    }
                    callback(response);
                })
                .catch(function (error) {
                    errorHandler(error,_this);
                });
        };
        Vue.prototype._get= function (path,callback,errorCallback) {
            let _this=this;

            let address=Constants.BASE_URL;
            axios
                .get(address + path,getConfig())
                .then(response => {
                    if(response.data.error !== 0) {
                        let error=new Error();
                        error.response=response;
                        throw error;
                    }
                    callback(response);
                })
                .catch(function (error) {
                    if(errorCallback!=null) {
                        errorCallback(error)
                    }else{
                        errorHandler(error,_this);
                    }
                });
        };
        Vue.prototype._delete = function (path,callback,errorCallback) {
            let _this=this;

            let address=Constants.BASE_URL;
            axios.delete(address + path,getConfig())
                .then(response => {
                    if(response.data.error !== 0) {
                        let error=new Error();
                        error.response=response;
                        throw error;
                    }
                    callback(response);
                })
                .catch(function (error) {
                    if(errorCallback!=null) {
                        errorCallback(error)
                    }else{
                        errorHandler(error,_this);
                    }
                });

        };
        Vue.prototype._patch = function (path, data, callback) {
            let _this=this;

            let address=Constants.BASE_URL;
            axios
                .patch(address + path, data,getConfig())
                .then(response => {
                    if(response.data.error !== 0) {
                        let error=new Error();
                        error.response=response;
                        throw error;
                    }
                    callback(response);
                })
                .catch(function (error) {
                    errorHandler(error,_this);
                });
        };

        function errorHandler(error,_this) {
            console.log(error.response);
            _this.$notify.error({
                title: '错误',
                message: error.response.data.message
            });
            if(error.response.data.error === 10000) {
                _this.$router.push('/login');
            }
            // if(error.response&&error.response.status!==404) {
            //     _this.$Message.error({content: error.response.data.message, duration: 5});
            // }else{
            //     console.log(error);
            //     _this.$Message.error({content: 'Failed to connect with your kong admin api,please check the address you input,or your computer has no permission to visit the admin api', duration: 10});
            //     _this.$router.push('/config');
            // }
        }

        function getConfig() {
            let config={};
            if(sessionStorage.headers==='null') {
                sessionStorage.removeItem('headers');
                return config;
            }
            if(sessionStorage.headers) {
                try{
                    config.headers=JSON.parse(sessionStorage.headers);
                }catch (e) {
                    sessionStorage.removeItem('headers');
                }

            }
            return config;
        }
    }
}
