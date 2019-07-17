<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-tickets"></i> 原始清单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-form ref="form" :inline="true" :model="form" label-width="80px">
                    <el-form-item label="鸽主编号">
                        <el-input v-model="form.number"></el-input>
                    </el-form-item>
                    <el-form-item label="足环号">
                        <el-input v-model="form.footNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号">
                        <el-input v-model="form.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="鸽主名称">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="search">搜索</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <div class="handle-box">
                <el-button type="primary" icon="search" @click="updown">下载模板</el-button>
                <el-button type="primary" icon="search" @click="upload">上传鸽子清单</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" ref="multipleTable">
                <el-table-column type="index" label="序号" width="50" align="center"></el-table-column>
                <el-table-column prop="number" label="鸽主编号">
                </el-table-column>
                <el-table-column prop="name" label="鸽主名称">
                </el-table-column>
                <el-table-column prop="address" label="地区">
                </el-table-column>
                 <el-table-column prop="footer" label="足环号">
                </el-table-column>
                 <el-table-column prop="color" label="羽色">
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="prev, pager, next" :total="1000">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'OriginList',
        data() {
            return {
                url: './static/vuetable.json',
                tableData: [
                {
                    "number": '000000000',
                    "name": '好鸽獒园-闫国强',
                    "address": '内蒙古呼和浩特',
                    "footer": '2018-05-0496821',
                    "color": '雨点',
                },
                {
                    "number": '000000001',
                    "name": '京大鸽舍-王文君',
                    "address": '北京朝阳',
                    "footer": '2018-03-0877712',
                    "color": '灰',
                },
                {
                    "number": '000000002',
                    "name": '天兵鸽舍·周文兵',
                    "address": '北京昌平',
                    "footer": '2018-01-1392930',
                    "color": '灰花',
                },
                {
                    "number": '000000004',
                    "name": '郭明超',
                    "address": '北京大兴',
                    "footer": '2018-01-0998753',
                    "color": '花',
                },
                {
                    "number": '000000005',
                    "name": '盛杰凯达鸽业-张顺+刘雅杰',
                    "address": '北京朝阳',
                    "footer": '2018-11-0249762',
                    "color": '雨点',
                },
                {
                    "number": '000000006',
                    "name": '锦州东海鸽舍',
                    "address": '辽宁锦州',
                    "footer": '2018-05-0496821',
                    "color": '灰',
                },
                {
                    "number": '055555555',
                    "name": '吴玉辉+杨立英',
                    "address": '河北廊坊',
                    "footer": '2018-05-0496821',
                    "color": '雨点',
                },

                ],
                cur_page: 1,
                form: {
                    name: '',
                    footNumber: '',
                    number: '',
                    phone: ''
                },
            }
        },
        created() {
            // this.getData();
        },
        computed: {

        },
        methods: {
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                // 开发环境使用 easy-mock 数据，正式环境使用 json 文件
                if (process.env.NODE_ENV === 'development') {
                    this.url = '/ms/table/list';
                };
                this.$axios.post(this.url, {
                    page: this.cur_page
                }).then((res) => {
                    this.tableData = res.data.list;
                })
            },
            search() {
                // 传入查询条件，请求查询
                // ...
            },
            formatter(row, column) {
                return row.address;
            },
            // 上传
            upload() {

            },
            // 下载
            updown() {

            }
        }
    }

</script>

<style scoped>
    .handle-box {
        margin-bottom: 20px;

    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
</style>
