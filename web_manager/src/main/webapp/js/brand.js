new Vue({
    el:"#app",
    data:{
        brandList:[],
        brand:{},
        page: 1,  //显示的是哪一页 当前角标
        pageSize: 10, //每一页显示的数据条数
        total: 0, //记录总数
        maxPage:9,
        selectedID:[],
        searchObj:{}
    },
    methods:{
        pageHandler:function (page) {
            this.page = page;
            var _this =this;
            axios.post("/brand/findPage.do?page="+this.page+"&pageSize="+this.pageSize,this.searchObj).then(function (response) {
                _this.brandList = response.data.rows;
                _this.total = response.data.total;
            }).catch(function (reason) {
                console.log(reason);
            });
        },
        save:function () {
            var _this = this;
            var url = '';
            if (_this.brand.id != null){
                url = '/brand/update.do' //更新
            } else {
                url = '/brand/add.do';  //添加
            }
            axios.post(url, _this.brand)
                .then(function (response) {
                    if (response.data.success){
                        alert(response.data.message);
                        //刷新页面
                        _this.pageHandler(1);
                    } else {
                        alert(response.data.message);
                    }
                })
        },

        findById:function (id) {
            var _this = this;
            axios.get("/brand/findOne.do",{params:{id:id}}).then(function (response) {
               _this.brand = response.data;
            }).catch(function (reason) {
                console.log(reason);
            });
        },
        deleteSelection:function (event,id) {
            //判断当前是否选中
            if(event.target.checked){
                //如果选中  添加记录id
                this.selectedID.push(id);
            }else {
                //如果不选中(取消选中)   原来 记录id 移除
                var idx = this.selectedID.indexOf(id);
                this.selectedID.splice(idx,1);
            }
        },
        deleteBrand:function () {
            var _this = this;
            axios.post("/brand/delete.do",Qs.stringify({ids:this.selectedID},{indices:false}))
                .then(function (response) {
                    if (response.data.success){
                        alert(response.data.message);
                        //刷新页面
                        _this.pageHandler(1);
                    } else {
                        alert(response.data.message);
                    }
                });

        }
    },
    created:function () {
        this.pageHandler(1);
    }

});