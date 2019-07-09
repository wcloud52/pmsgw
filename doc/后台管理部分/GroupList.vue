<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-tickets"></i> 分组清单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-form ref="form" :inline="true" :model="form" label-width="80px">
                    <el-form-item label="鸽主编号">
                        <el-input v-model="form.number"></el-input>
                    </el-form-item>
                    <el-form-item label="鸽主名称">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="足环号">
                        <el-input v-model="form.footNumber"></el-input>
                    </el-form-item>
                    <el-form-item label="分组状态">
                        <el-select v-model="form.status">
                            <el-option label="全部" value="全部"></el-option>
                            <el-option label="未分组" value="未分组"></el-option>
                            <el-option label="已分组" value="已分组"></el-option>
                            <el-option label="没资格" value="没资格"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="search">搜索</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <div class="handle-box">
                <el-button type="primary" icon="search" @click="updown">下载模板</el-button>
                <el-button type="primary" icon="search" @click="upload">上传鸽子清单</el-button>
                <el-button type="primary" icon="search" @click="group">分组</el-button>
                <el-button type="primary" icon="search" @click="exportList">导出分组列表</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%" height="300">
                <el-table-column type="index" label="序号" width="50" align="center" fixed></el-table-column>
                <el-table-column prop="number" label="鸽主编号" width="100" fixed>
                </el-table-column>
                <el-table-column prop="name" label="鸽主名称" width="200" fixed>
                </el-table-column>
                <el-table-column prop="footer" label="足环号" width="150" fixed>
                </el-table-column>
                 
                 <el-table-column prop="group" label="分组情况" width="80" fixed>
                </el-table-column>
                 <el-table-column prop="grouper" label="分组人" width="80">
                </el-table-column>
                <el-table-column prop="time" label="分组人微信号" width="150">
                </el-table-column>
                <el-table-column prop="grouptime" label="分组时间" width="150">
                </el-table-column>
                <el-table-column prop="groupend" label="分组结束时间" width="150">
                </el-table-column>
                <el-table-column prop="smallgroup" label="分组" width="80" :formatter="groupFmt">
                </el-table-column>
                <!-- <el-table-column prop="biggroup" label="大组" width="100">
                </el-table-column> -->
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="prev, pager, next" :total="1000">
                </el-pagination>
            </div>
        </div>

        <!-- 分组弹出框 -->
        <el-dialog title="分组" :visible.sync="groupVisible" width="84%">
            <el-form ref="groupForm" :model="groupForm" :inline="true">
                <el-form-item label="大组数">
                    <el-input v-model="form.bigGroup"></el-input>
                </el-form-item>
                <el-form-item label="小组数">
                    <el-input v-model="form.smallGroup"></el-input>
                </el-form-item>
                <el-form-item label="分组时间">
                    <el-date-picker v-model="form.time" type="datetimerange" range-separator="至"  start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search">确认</el-button>
                </el-form-item>
            </el-form>
            <el-tabs v-model="activeName" @tab-click="handleTabClick">
                <el-tab-pane label="未分组" name="notGrouped">
                    <el-row :gutter="20">
                        <el-col :span="6" :offset="18">
                            <el-input v-model="notGrouped_schfilter" placeholder="搜索"></el-input>
                        </el-col>
                    </el-row>
                    <el-table :data="notGrouped_groupTables" :span-method="notGroupedMerge" border style="width: 100%;">
                        <el-table-column prop="number" label="鸽主编号" width="180"></el-table-column>
                        <el-table-column prop="name" label="鸽主名称"></el-table-column>
                        <el-table-column prop="originCount" label="原始交鸽数"></el-table-column>
                        <el-table-column prop="footerRing" label="赛鸽足环"></el-table-column>
                        <el-table-column prop="smallGroup" label="小组">
                            <template slot-scope="scope">
                                <el-radio-group v-model="scope.row.small">
                                    <el-radio :label="item" v-for="item in scope.row.smallGroup">{{item}}</el-radio>
                                </el-radio-group>
                            </template>
                        </el-table-column>
                        <el-table-column prop="bigGroup" label="大组">
                            <template slot-scope="scope">
                                <el-radio-group v-model="scope.row.big">
                                    <el-radio :label="item" v-for="item in scope.row.bigGroup">{{item}}</el-radio>
                                </el-radio-group>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
                <el-tab-pane label="已分组" name="hasGrouped">
                    <el-row :gutter="20">
                        <el-col :span="6" :offset="18">
                            <el-input v-model="hasGrouped_schfilter" placeholder="搜索"></el-input>
                        </el-col>
                    </el-row>
                    <el-table :data="hasGrouped_groupTables" :span-method="hasGroupedMerge" border style="width: 100%;">
                        <el-table-column prop="number" label="鸽主编号" width="180"></el-table-column>
                        <el-table-column prop="name" label="鸽主名称"></el-table-column>
                        <el-table-column prop="originCount" label="原始交鸽数"></el-table-column>
                        <el-table-column prop="footerRing" label="赛鸽足环"></el-table-column>
                        <el-table-column prop="smallGroup" label="小组">
                            <template slot-scope="scope">
                                <el-radio-group v-model="scope.row.small">
                                    <el-radio :label="item" v-for="item in scope.row.smallGroup">{{item}}</el-radio>
                                </el-radio-group>
                            </template>
                        </el-table-column>
                        <el-table-column prop="bigGroup" label="大组">
                            <template slot-scope="scope">
                                <el-radio-group v-model="scope.row.big">
                                    <el-radio :label="item" v-for="item in scope.row.bigGroup">{{item}}</el-radio>
                                </el-radio-group>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
                <el-tab-pane label="没资格" name="notQualified">
                    <el-row :gutter="20">
                        <el-col :span="6" :offset="18">
                            <el-input v-model="notQualified_schfilter" placeholder="搜索"></el-input>
                        </el-col>
                    </el-row>
                    <el-table :data="notQualified_groupTables" :span-method="notQualifiedMerge" border style="width: 100%;">
                        <el-table-column prop="number" label="鸽主编号" width="180"></el-table-column>
                        <el-table-column prop="name" label="鸽主名称"></el-table-column>
                        <el-table-column prop="originCount" label="原始交鸽数"></el-table-column>
                        <el-table-column prop="footerRing" label="赛鸽足环"></el-table-column>
                        <el-table-column prop="smallGroup" label="小组">
                            <template slot-scope="scope">
                                <el-radio-group v-model="scope.row.small">
                                    <el-radio :label="item"  disabled v-for="item in scope.row.smallGroup">{{item}}</el-radio>
                                </el-radio-group>
                            </template>
                        </el-table-column>
                        <el-table-column prop="bigGroup" label="大组">
                            <template slot-scope="scope">
                                <el-radio-group v-model="scope.row.big">
                                    <el-radio :label="item"  disabled v-for="item in scope.row.bigGroup">{{item}}</el-radio>
                                </el-radio-group>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-tab-pane>
            </el-tabs>
            <span slot="footer" class="dialog-footer">
                <el-button @click="groupVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: 'basetable',
        data() {
            return {
                url: './static/vuetable.json',
                tableData: [
                {
                  "number": '000000000',
                    "name": '好鸽獒园-闫国强',
                    "footer": '2018-05-0496821',
                    "time": '2019-4-25 15:00',
                    "group": '已分组',
                    "grouper": '管理员',
                    "grouptime": '2019-5-1 00:00',
                    "groupend": '2019-5-1 00:00',
                    "smallgroup": 'b',
                    "biggroup": 'A',
                },
                {
                  "number": '000000001',
                    "name": '京大鸽舍-王文君',
                    "footer": '2018-05-0496821',
                    "time": '2019-4-26 15:00',
                    "group": '已分组',
                    "grouper": '管理员',
                    "grouptime": '2019-5-1 00:00',
                    "groupend": '2019-5-1 00:00',
                    "smallgroup": 'b',
                    "biggroup": 'A',
                },
                {
                  "number": '000000002',
                    "name": '天兵鸽舍·周文兵',
                    "footer": '2018-05-0496821',
                    "time": '2019-4-27 15:00',
                    "group": '已分组',
                    "grouper": '管理员',
                    "grouptime": '2019-5-1 00:00',
                    "groupend": '2019-5-1 00:00',
                    "smallgroup": 'b',
                    "biggroup": 'A',
                },
                {
                  "number": '000000004',
                    "name": '郭明超',
                    "footer": '2018-05-0496821',
                    "time": '2019-4-25 17:00',
                    "group": '已分组',
                    "grouper": '管理员',
                    "grouptime": '2019-5-1 00:00',
                    "groupend": '2019-5-1 00:00',
                    "smallgroup": 'b',
                    "biggroup": 'A',
                },
                {
                  "number": '000000005',
                    "name": '盛杰凯达鸽业-张顺+刘雅杰',
                    "footer": '2018-05-0496821',
                    "time": '2019-4-25 5:00',
                    "group": '已分组',
                    "grouper": '管理员',
                    "grouptime": '2019-5-1 00:00',
                    "groupend": '2019-5-1 00:00',
                    "smallgroup": 'b',
                    "biggroup": 'A',
                },
                {
                  "number": '055555555',
                    "name": '吴玉辉+杨立英',
                    "footer": '2018-05-0496821',
                    "time": '2019-4-25 15:30',
                    "group": '已分组',
                    "grouper": '管理员',
                    "grouptime": '2019-5-1 00:00',
                    "groupend": '2019-5-1 00:00',
                    "smallgroup": 'b',
                    "biggroup": 'A',
                },
                {
                  "number": '000000006',
                    "name": '锦州东海鸽舍',
                    "footer": '2018-05-0496821',
                    "time": '2019-4-20 15:00',
                    "group": '已分组',
                    "grouper": '管理员',
                    "grouptime": '2019-5-1 00:00',
                    "groupend": '2019-5-1 00:00',
                    "smallgroup": 'b',
                    "biggroup": 'A',
                }
                ],
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                groupVisible: false,
                form: {
                    name: '',
                    footNumber: '',
                    number: '',
                    status: '全部'
                },
                groupForm: {
                    bigGroup: '',
                    smallGroup: '',
                    time: [new Date(2000, 10, 10, 10, 10), new Date(2000, 10, 11, 10, 10)],
                },
                activeName: 'notGrouped',
                notGrouped_groupTabData: [{number: '0001',
                      name: '张三',
                      originCount: '45',
                      footerRing: '012',
                      small: 'a',
                      smallGroup: ['a', 'b', 'c'],
                      big: 'A',
                      bigGroup: ['A', 'B']
                }, {
                      number: '0001',
                      name: '张三',
                      originCount: '45',
                      footerRing: '123',
                      small: 'a',
                      smallGroup: ['a', 'b', 'c'],
                      big: 'A',
                      bigGroup: ['A', 'B']
                }, {
                      number: '0001',
                      name: '张三',
                      originCount: '45',
                      footerRing: '234',
                      small: 'a',
                      smallGroup: ['a', 'b', 'c'],
                      big: 'A',
                      bigGroup: ['A', 'B']
                }, {
                      number: '0001',
                      name: '张三',
                      originCount: '45',
                      footerRing: '345',
                      small: 'a',
                      smallGroup: ['a', 'b', 'c'],
                      big: 'A',
                      bigGroup: ['A', 'B']
                }, {
                      number: '0002',
                      name: '李四',
                      originCount: '27',
                      footerRing: '789',
                      small: 'a',
                      smallGroup: ['a', 'b'],
                      big: 'A',
                      bigGroup: ['A']
                }, {
                      number: '0002',
                      name: '李四',
                      originCount: '27',
                      footerRing: '654',
                      small: 'a',
                      smallGroup: ['a', 'b'],
                      big: 'A',
                      bigGroup: ['A']
                }, {
                      number: '0002',
                      name: '李四',
                      originCount: '27',
                      footerRing: '333',
                      small: 'a',
                      smallGroup: ['a', 'b'],
                      big: 'A',
                      bigGroup: ['A']
                }, {
                      number: '0003',
                      name: '王五',
                      originCount: '34',
                      footerRing: '333',
                      small: 'a',
                      smallGroup: ['a', 'b'],
                      big: 'A',
                      bigGroup: ['A']
                }, {
                      number: '0003',
                      name: '王五',
                      originCount: '34',
                      footerRing: '909',
                      small: 'a',
                      smallGroup: ['a', 'b'],
                      big: 'A',
                      bigGroup: ['A']
                }],// 未分组
                hasGrouped_groupTabData: [{number: '0001',
                      name: '张三',
                      originCount: '45',
                      footerRing: '012',
                      small: 'a',
                      smallGroup: ['a', 'b', 'c'],
                      big: 'A',
                      bigGroup: ['A', 'B']
                }, {
                      number: '0001',
                      name: '张三',
                      originCount: '45',
                      footerRing: '123',
                      small: 'a',
                      smallGroup: ['a', 'b', 'c'],
                      big: 'A',
                      bigGroup: ['A', 'B']
                }, {
                      number: '0001',
                      name: '张三',
                      originCount: '45',
                      footerRing: '234',
                      small: 'a',
                      smallGroup: ['a', 'b', 'c'],
                      big: 'A',
                      bigGroup: ['A', 'B']
                }, {
                      number: '0001',
                      name: '张三',
                      originCount: '45',
                      footerRing: '345',
                      small: 'a',
                      smallGroup: ['a', 'b', 'c'],
                      big: 'A',
                      bigGroup: ['A', 'B']
                }, {
                      number: '0002',
                      name: '李四',
                      originCount: '27',
                      footerRing: '789',
                      small: 'a',
                      smallGroup: ['a', 'b'],
                      big: 'A',
                      bigGroup: ['A']
                }, {
                      number: '0002',
                      name: '李四',
                      originCount: '27',
                      footerRing: '654',
                      small: 'a',
                      smallGroup: ['a', 'b'],
                      big: 'A',
                      bigGroup: ['A']
                }],// 已分组
                notQualified_groupTabData: [{number: '0001',
                      name: '张三',
                      originCount: '45',
                      footerRing: '012',
                      small: 'a',
                      smallGroup: ['a', 'b', 'c'],
                      big: 'A',
                      bigGroup: ['A', 'B']
                }, {
                      number: '0001',
                      name: '张三',
                      originCount: '45',
                      footerRing: '345',
                      small: 'a',
                      smallGroup: ['a', 'b', 'c'],
                      big: 'A',
                      bigGroup: ['A', 'B']
                }, {
                      number: '0002',
                      name: '李四',
                      originCount: '27',
                      footerRing: '789',
                      small: 'a',
                      smallGroup: ['a', 'b'],
                      big: 'A',
                      bigGroup: ['A']
                }, {
                      number: '0002',
                      name: '李四',
                      originCount: '27',
                      footerRing: '654',
                      small: 'a',
                      smallGroup: ['a', 'b'],
                      big: 'A',
                      bigGroup: ['A']
                }, {
                      number: '0003',
                      name: '王五',
                      originCount: '34',
                      footerRing: '909',
                      small: 'a',
                      smallGroup: ['a', 'b'],
                      big: 'A',
                      bigGroup: ['A']
                }],// 没资格
                
                spanArr: {// 用作表格合并
                    notGrouped: [],
                    hasGrouped: [],
                    notQualified: [],
                },
                pos: {// 用作表格合并
                    notGrouped: 0,
                    hasGrouped: 0,
                    notQualified: 0,
                },
                notGrouped_schfilter: '',
                hasGrouped_schfilter: '',
                notQualified_schfilter: '',
            }
        },
        created() {
            // this.getData();
        },
        computed: {
            notGrouped_groupTables:function() {
                var schfilter = this.notGrouped_schfilter;
                if (schfilter) {
                    return  this.notGrouped_groupTabData.filter( function(item) {
                        return Object.keys(item).some( function(key) {
                            return String(item[key]).toLowerCase().indexOf(schfilter) > -1;
                        })
                    })
                }
                return this.notGrouped_groupTabData;
              },
              hasGrouped_groupTables:function() {
                var schfilter = this.hasGrouped_schfilter;
                if (schfilter) {
                    return  this.hasGrouped_groupTabData.filter( function(item) {
                        return Object.keys(item).some( function(key) {
                            return String(item[key]).toLowerCase().indexOf(schfilter) > -1;
                        })
                    })
                }
                return this.hasGrouped_groupTabData;
              },
              notQualified_groupTables:function() {
                var schfilter = this.notQualified_schfilter;
                if (schfilter) {
                    return  this.notQualified_groupTabData.filter( function(item) {
                        return Object.keys(item).some( function(key) {
                            return String(item[key]).toLowerCase().indexOf(schfilter) > -1;
                        })
                    })
                }
                return this.notQualified_groupTabData;
              },
        },
        watch: {

        },
        mounted(){
            this.mountedMerge(this.notGrouped_groupTabData, this.spanArr.notGrouped, this.pos.notGrouped),
            this.mountedMerge(this.hasGrouped_groupTabData, this.spanArr.hasGrouped, this.pos.hasGrouped),
            this.mountedMerge(this.notQualified_groupTabData, this.spanArr.notQualified, this.pos.notQualified)
        },
        methods: {
            // mounted() 中通用的方法 合并表格
            mountedMerge(groupTabData, spanArr, pos) {
                groupTabData.forEach( (item,index) => {
                    console.log(item.number);
                    if (index === 0) {
                        spanArr.push(1);
                        pos = 0;
                    } else {
                        if (item.number === groupTabData[index-1].number) {
                            spanArr[pos] += 1;
                            spanArr.push(0);
                        } else {
                            pos = index;
                            spanArr.push(1);
                        }
                    }
                })
                return groupTabData;
            },
            notGroupedMerge({ row, column, rowIndex, columnIndex }) {
                if (columnIndex < 3) {
                    if (this.spanArr.notGrouped[rowIndex]) {
                        return {
                            rowspan:this.spanArr.notGrouped[rowIndex],
                            colspan:1
                        }
                    } else {
                        return {
                            rowspan: 0,
                            colspan: 0
                        }

                    }
                }
            },
            hasGroupedMerge({ row, column, rowIndex, columnIndex }) {
                if (columnIndex < 3) {
                    if (this.spanArr.hasGrouped[rowIndex]) {
                        return {
                            rowspan:this.spanArr.hasGrouped[rowIndex],
                            colspan:1
                        }
                    } else {
                        return {
                            rowspan: 0,
                            colspan: 0
                        }

                    }
                }
            },
            notQualifiedMerge({ row, column, rowIndex, columnIndex }) {
                if (columnIndex < 3) {
                    if (this.spanArr.notQualified[rowIndex]) {
                        return {
                            rowspan:this.spanArr.notQualified[rowIndex],
                            colspan:1
                        }
                    } else {
                        return {
                            rowspan: 0,
                            colspan: 0
                        }

                    }
                }
            },
            handleTabClick(tab, event) {
                console.log(tab, event);
            },
            group() {
                this.groupVisible = true;
            },
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getData();
            },
            groupFmt(row, column, cellValue, index) {
              return (row.smallgroup + row.biggroup) 
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
                this.is_search = true;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleEdit(index, row) {
                this.idx = index;
                const item = this.tableData[index];
                this.form = {
                    name: item.name,
                    date: item.date,
                    address: item.address
                }
                this.editVisible = true;
            },
            handleDelete(index, row) {
                this.idx = index;
                this.delVisible = true;
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            // 保存编辑
            saveEdit() {
                this.$set(this.tableData, this.idx, this.form);
                this.editVisible = false;
                this.$message.success(`修改第 ${this.idx+1} 行成功`);
            },
            // 确定删除
            deleteRow(){
                this.tableData.splice(this.idx, 1);
                this.$message.success('删除成功');
                this.delVisible = false;
            },
            // 上传
            upload() {},
            // 下载
            updown() {},
            // 导出
            exportList() {},
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
