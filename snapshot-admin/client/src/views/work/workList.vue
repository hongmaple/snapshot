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
      <el-table :data="userData.list" border style="width: 100%">
        <el-table-column label="标题" align="center" width="180">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.title }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="视频" align="center" width="350">
          <template slot-scope="scope">
            <video width="320" height="240" controls="controls">
              <source :src="'api/'+scope.row.url" type="video/mp4">
            </video>
          </template>
        </el-table-column> -->
        <el-table-column label="状态" align="center" width="350">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.status=='TO_AUDIT'? '待审核':(scope.row.status=='PASS'?'通过':(scope.row.status=='NO_PASS'?'不通过':'失效')) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="文件类型" align="center" width="350">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.type==1? '图片':'视频' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="作品类型" align="center" width="350">
          <template slot-scope="scope">
            <span style="margin-left: 10px">{{ scope.row.workType==1? '文明点赞':'爆光台' }}</span>
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
    <VideoTeachingDialong :dialong="dialong" :form="form" :id="id" @UserData="loadCurrentPageUserList">
    </VideoTeachingDialong>
    <div class="page">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="userData.pageNum" :page-sizes="userData.page_sizes" :page-size="userData.pageSize"
        :layout="userData.layout" :total="userData.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src
import VideoTeachingDialong from "../../components/VideoTeachingDialong";
export default {
  name: "VideoTeaching",
  data() {
    return {
      userData: {
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
        title: null,
        url: [],
        type: 1,
        status: null
      },
      id: 0
    };
  },
  methods: {
    userInfo(formData) {
      this.$axios
        .post("/api/work/list", formData)
        .then(res => {
          this.userData = res.data.data;
        })
        .catch(err => console.log(err));
    },
    loadCurrentPageUserList() {
      let pageSize = this.userData.pageSize;
      let page = this.userData.pageNum;
      const formData = {
        isAsc: null,
        orderBy: null,
        orderByColumn: null,
        pageNum: page,
        pageSize: pageSize
      };
      this.userInfo(formData);
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
        title: row.title,
        url: JSON.parse(row.url),
        type: row.type,
        status: row.status,
        workType: row.workType,
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
      this.userData.pageNum = 1; //第一页
      this.userData.pageSize = page_size; //每页先显示多少数据
    },
    handleCurrentChange(page) {
      // 跳转页数
      //获取每行数
      let pageSize = this.userData.pageSize;
      const formData = {
        isAsc: null,
        orderBy: null,
        orderByColumn: null,
        pageNum: page,
        pageSize: pageSize
      };
      this.userInfo(formData);
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
    this.userInfo(formData);
  },
  components: {
    VideoTeachingDialong
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
