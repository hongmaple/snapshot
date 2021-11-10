<template>
  <div class="staff">
    <div class="staff-top">
        <el-form :inline="true">
            <el-form-item class="btnRight">
                <el-button type="primary" size ="small" icon="el-icon-edit-outline" @click='onAddMoney()'>添加</el-button>
            </el-form-item>
        </el-form>
    </div>
      <div class="tables">
           <el-table
            :data="pictureData.list"
            border
            style="width: 100%">
            <el-table-column
                label="id"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.id }}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="图片"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <img alt="图片" style="width:100px;height=80px;" :src="'api/'+scope.row.pictureUrl"/>
                </template>
            </el-table-column>
            <el-table-column
                label="排序"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.pictureIndex }}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="状态"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.pictureStatus==1?'有效':'无效' }}</span>
                </template>
            </el-table-column>
            
            <el-table-column
                label="创建时间"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.createTime | moment }}</span>
                </template>
            </el-table-column>

            <el-table-column
                label="更新时间"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.updateTime | moment }}</span>
                </template>
            </el-table-column>
            
            <el-table-column label="操作" fixed="right">
                <template slot-scope="scope">
                    <el-button
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button
                    size="mini"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
      </div>
      <pictureDialong :dialong="dialong" :form="form" :id="id" @pictureData="loadCurrentPageUserList"></pictureDialong>
      <div class="page">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pictureData.pageNum"
          :page-sizes="pictureData.page_sizes"
          :page-size="pictureData.pageSize"
          :layout="pictureData.layout"
          :total="pictureData.total">
        </el-pagination>
	 	  </div>
  </div>
</template>

<script>
// @ is an alias to /src
import pictureDialong from "../components/pictureDialong";
export default {
  name: "Staff",
  data() {
    return {
      pictureData: {
        list: [],
        pageNum: 1,
        pageSize: 5,
        pages: 0,
        total: 0,
        page_sizes:[5,10,15,20], //每页显示多少条
		layout:'total, sizes, prev, pager, next, jumper'
      }, //数据存储
      dialong: {
        //弹出框
        show: false,
        title: "",
        option: "edit"
      },
      dialong2: {
        //弹出框
        show: false,
        title: "",
        option: "edit"
      },
      form: {   //添加和删除需要传递的字段名
         id: null,
         pictureUrl: null,
         pictureIndex: 1,
         pictureStatus: 1
      },
      id: 0
    };
  },
  methods: {
    queryPictureList(formData) {
      this.$axios
        .post("/api/picture/query",formData,{headers: {"token": localStorage.getItem("eleToken")}})
        .then(res => {
          this.pictureData = res.data.data;
        }).catch(err => console.log(err));
    },
    loadCurrentPageUserList() {
	    let pageSize = this.pictureData.pageSize;
        let page = this.pictureData.pageNum;
				const formData = {
                "isAsc": null,
                "orderBy": null,
                "orderByColumn": null,
                "pageNum": page,
                "pageSize": pageSize
        }
        this.queryPictureList(formData);
    },
    handleEdit(index, row) {
      //编辑
      this.dialong = {
        title: "编辑信息",
        show: true,
        option:"edit"
      }
      this.id = row.id;
      this.form = {
          id: row.id,
          pictureUrl: row.pictureUrl,
          pictureIndex: row.pictureIndex,
          pictureStatus: row.pictureStatus+''
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
          this.$axios.delete(`/api/user/${row.id}`,{headers: {"token": localStorage.getItem("eleToken")}})
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
        option:"add"
      }
      this.form = {
          id: null,
          pictureUrl: null,
          pictureIndex: 1,
          pictureStatus: 1+''
      }
    },
		handleSizeChange(page_size) {
				this.pictureData.pageNum = 1; //第一页
				this.pictureData.pageSize = page_size; //每页先显示多少数据
		},
		handleCurrentChange(page){
				// 跳转页数
				//获取每行数
				let pageSize = this.pictureData.pageSize;
				const formData = {
                "isAsc": null,
                "orderBy": null,
                "orderByColumn": null,
                "pageNum": page,
                "pageSize": pageSize
        }
        this.queryPictureList(formData);
		}
  },
  created() {
     const formData = {
                "isAsc": null,
                "orderBy": null,
                "orderByColumn": null,
                "pageNum": 1,
                "pageSize": 5
    }
    this.queryPictureList(formData);
  },
  components: {
    pictureDialong
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
