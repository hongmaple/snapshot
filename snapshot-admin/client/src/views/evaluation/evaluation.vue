<template>
  <div class="staff">
    <div class="staff-top">
      <el-form :inline="true">
        <!-- <el-form-item class="btnRight">
          <el-button type="primary" size="small" icon="el-icon-edit-outline" @click='onAddMoney()'>添加</el-button>
        </el-form-item> -->
      </el-form>
    </div>
    <div class="tables">
      <el-table :data="evaluationData.list" border style="width: 100%">
        <el-table-column label="id" align="center" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" width="350">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.status=='TO_AUDIT'? '待审核':(scope.row.status=='PASS'?'通过':(scope.row.status=='NO_PASS'?'不通过':'失效')) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="评论者" align="center" width="350">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.commentator}}</span>
          </template>
        </el-table-column>
        <el-table-column label="评论内容" align="center" width="350">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.content}}</span>
          </template>
        </el-table-column>
        <el-table-column
                label="评论时间"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.createTime | moment }}</span>
                </template>
            </el-table-column>
        <el-table-column label="操作" fixed="right">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">查看</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <evaluationDialong :dialong="dialong" :form="form" :id="id" @evaluationData="loadCurrentPageUserList">
    </evaluationDialong>
    <div class="page">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="evaluationData.pageNum" :page-sizes="evaluationData.page_sizes" :page-size="evaluationData.pageSize"
        :layout="evaluationData.layout" :total="evaluationData.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import evaluationDialong from "../../components/evaluationDialong";
export default {
  name: "VideoTeaching",
  data() {
    return {
      evaluationData: {
        list: [],
        pageNum: 1,
        pageSize: 5,
        pages: 0,
        total: 0,
        page_sizes: [5, 10, 15, 20], //每页显示多少条
        layout: "total, sizes, prev, pager, next, jumper"
      }, //数据存储
      dialong: {
        //弹出框
        show: false,
        title: "",
        option: "edit"
      },
      form: {
        //添加和删除需要传递的字段名
        id: 0,
        commentator: null,
        status: null,
        content: null
      },
      id: 0
    };
  },
  methods: {
    evaluationList(formData) {
      this.$axios
        .post("/api/evaluation/admin/list", formData)
        .then(res => {
          this.evaluationData = res.data.data;
        })
        .catch(err => console.log(err));
    },
    loadCurrentPageUserList() {
      let pageSize = this.evaluationData.pageSize;
      let page = this.evaluationData.pageNum;
      const formData = {
        isAsc: null,
        orderBy: null,
        orderByColumn: null,
        pageNum: page,
        pageSize: pageSize
      };
      this.evaluationList(formData);
    },
    handleEdit(index, row) {
      //编辑
      this.dialong = {
        title: "查看信息",
        show: true,
        option: "edit"
      };
      this.id = row.id;
      this.form ={
        id: row.id,
        content: row.content,
        status: row.status,
        commentator: row.commentator,
      }
    },
    handleDelete(index, row) {
      //删除数据
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$axios
            .delete(`/api/work/${row.id}`, {
              headers: { token: localStorage.getItem("eleToken") }
            })
            .then(res => {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              this.loadCurrentPageUserList();
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    onAddMoney() {
      //添加内容
      this.dialong = {
        title: "添加信息",
        show: true,
        option: "add"
      };
      this.form = {
        id: 0,
        title: null,
        url: []
      };
    },
    handleSizeChange(page_size) {
      this.evaluationData.pageNum = 1; //第一页
      this.evaluationData.pageSize = page_size; //每页先显示多少数据
    },
    handleCurrentChange(page) {
      // 跳转页数
      //获取每行数
      let pageSize = this.evaluationData.pageSize;
      const formData = {
        isAsc: null,
        orderBy: null,
        orderByColumn: null,
        pageNum: page,
        pageSize: pageSize
      };
      this.evaluationList(formData);
    }
  },
  created() {
    const formData = {
      isAsc: null,
      orderBy: null,
      orderByColumn: null,
      pageNum: 1,
      pageSize: 5
    };
    this.evaluationList(formData);
  },
  components: {
    evaluationDialong
  }
};
</script>
<style scoped>
.staff {
  margin: 10px;
}
.btnRight {
  float: right;
}
</style>
