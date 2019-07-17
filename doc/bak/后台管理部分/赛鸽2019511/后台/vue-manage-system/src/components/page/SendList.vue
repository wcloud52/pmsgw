<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-message"></i> 群发短信</el-breadcrumb-item>
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
                    <el-form-item label="手机号">
                        <el-input v-model="form.phone"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="search">搜索</el-button>
                    </el-form-item>
                </el-form>
            </div>

            <div class="handle-box">
                <el-button type="primary" icon="search" @click="search">下载模板</el-button>
                <el-button type="primary" icon="search" @click="search">上传信息</el-button>
                <el-button type="primary" icon="search" @click="handleAdd">新增</el-button>
                <el-button type="primary" icon="search" @click="handleEdit">编辑</el-button>
                <el-button type="primary" icon="search" @click="handleDelete">删除</el-button>
                <el-button type="success" icon="search" @click="sendMessage">群发短息</el-button>
            </div>
            <el-table :data="tableData" border style="width: 100%"  ref="multipleTable" @selection-change="handleSelectionChange">
                
                <el-table-column type="index" label="序号" align="center"></el-table-column>
                <el-table-column type="selection" align="center"></el-table-column>
                <el-table-column prop="number" label="鸽主编号">
                </el-table-column>
                <el-table-column prop="name" label="鸽主名称">
                </el-table-column>
                <el-table-column prop="phone" label="手机号">
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @current-change="handleCurrentChange" layout="prev, pager, next" :total="1000">
                </el-pagination>
            </div>
        </div>

        <!-- 新建弹出框 -->
        <el-dialog title="添加" :visible.sync="addVisible" width="50%">
            <el-table :data="messageData" border style="width: 100%">
                <el-table-column prop="number" label="鸽主编号">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.number"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="鸽主名称">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.name"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="phone" label="手机号">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.phone"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="address" label="操作">
                    <template slot-scope="scope">
                        <el-button @click="removeItem(scope.$index, scope.row)" v-if="scope.$index != 0" size="small" type="danger" icon="el-icon-minus" circle></el-button>
                        <el-button @click="addItem" v-if="scope.$index == messageData.length - 1" size="small" type="success" icon="el-icon-plus" circle></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="addVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveAdd">提 交</el-button>
            </span>
        </el-dialog>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="50%">
            <el-table :data="messageEditData" border style="width: 100%">
                <el-table-column prop="number" label="鸽主编号">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.number"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="鸽主名称">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.name"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="phone" label="手机号">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.phone"></el-input>
                    </template>
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 群发信息弹出框 -->
        <el-dialog title="群发短信" :visible.sync="msgVisible" width="50%">
            <el-form ref="form" :model="msgform" label-width="100px">
                <el-form-item label="群发短信ID">
                    <el-col :span="6">
                        <el-input v-model="msgform.msgid" @input="handleMessageChange"></el-input>
                    </el-col>  
                </el-form-item>
                <el-form-item label="内容">
                    <el-col :span="18">
                        <el-input type="textarea" v-model="msgform.content"></el-input>
                    </el-col>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="msgVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveSend">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: 'SendList',
        data() {
            return {
                url: './static/vuetable.json',
                tableData: [
                {
                    "number": '000000000',
                    "name": '好鸽獒园-闫国强',
                    "phone": '17923457584',
                    "color": '雨点',
                },
                {
                    "number": '000000001',
                    "name": '京大鸽舍-王文君',
                    "address": '北京朝阳',
                    "phone": '17923457584',
                    "footer": '17923457584',
                    "color": '灰',
                },
                {
                    "number": '000000002',
                    "name": '天兵鸽舍·周文兵',
                    "address": '北京昌平',
                    "phone": '17923457584',
                    "color": '灰花',
                },
                {
                    "number": '000000004',
                    "name": '郭明超',
                    "address": '北京大兴',
                    "phone": '17923457584',
                    "color": '花',
                },
                {
                    "number": '000000005',
                    "name": '盛杰凯达鸽业-张顺+刘雅杰',
                    "address": '北京朝阳',
                    "phone": '17923457584',
                    "color": '雨点',
                },
                {
                    "number": '000000006',
                    "name": '锦州东海鸽舍',
                    "address": '辽宁锦州',
                    "phone": '17923457584',
                    "color": '灰',
                },
                {
                    "number": '055555555',
                    "name": '吴玉辉+杨立英',
                    "address": '河北廊坊',
                    "phone": '17923457584',
                    "color": '雨点',
                },
                ],
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                addVisible: false,
                editVisible: false,
                delVisible: false,
                msgVisible: false,
                form: {
                    name: '',
                    number: '',
                    phone: ''
                },
                messageData: [
                    {
                        number: '',
                        name: '',
                        phone: ''
                    }
                ],
                messageEditData: [],
                multipleSelection: [],
                msgform: {
                    msgid: '',
                    content: ''
                },
            }
        },
        created() {
            // this.getData();
        },
        computed: {
            
        },
        methods: {
            handleSelectionChange(val) {
                 this.multipleSelection = val // 返回的是选中的列的数组集合

            },
            handleEdit() {
                this.messageEditData = [];
                console.log( this.multipleSelection);
                var multipleSelection = this.multipleSelection;
                if (multipleSelection.length > 0) {
                    for (var i = 0; i < multipleSelection.length; i++) {
                        var obj = {
                            number: multipleSelection[i].number,
                            name: multipleSelection[i].name,
                            phone: multipleSelection[i].phone
                        };
                        this.messageEditData.push(obj);
                    }
                    this.editVisible = true;
                } else {
                    this.$message({
                        message: '请先选择用户！',
                        type: 'warning'
                    });
                }

                
            },
            handleDelete() {
                var multipleSelection = this.multipleSelection;
                if (multipleSelection.length > 0) {
                    // 获取到选中的id集合
                    this.delVisible = true;
                } else {
                    this.$message({
                        message: '请先选择用户！',
                        type: 'warning'
                    });
                }
                
            },
            handleAdd() {
                this.addVisible = true;
            },
            removeItem(index, row) {
                this.messageData.splice(index, 1);
            },
            addItem() {
                this.messageData.push({
                    number: '',
                    name: '',
                    phone: ''
                })
            },
            sendMessage() {
                var multipleSelection = this.multipleSelection;
                if (multipleSelection.length > 0) {
                    // 获取到选中的id集合
                    this.msgVisible = true;
                } else {
                    this.$message({
                        message: '请先选择用户！',
                        type: 'warning'
                    });
                }
                
            },
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
                this.is_search = true;
            },
            formatter(row, column) {
                return row.address;
            },
            // 保存新建
            saveAdd() {

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
            // 输入群发短息id动态展示内容
            handleMessageChange(val) {
                console.log(val);
            },
            // 群发消息
            saveSend() {

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
