<template>
  <div class="nofind">
    <el-dialog
      :title="dialong.title"
      type="primary"
      size="small"
      :close-on-press-escape="false"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :visible.sync="dialong.show"
    >
      <el-form :model="form" ref="formdoalog" label-width="80px">
        <el-form-item label="评价者" prop="title">
          <span>{{form.commentator}}</span>
        </el-form-item>
         <el-form-item label="状态" prop="title">
          <span style="margin-left: 10px">{{ form.status=='TO_AUDIT'? '待审核':(form.status=='PASS'?'通过':(form.status=='NO_PASS'?'不通过':'失效')) }}</span>
        </el-form-item>
        <el-form-item label="评价内容" prop="title">
          <span style="margin-left: 10px">{{ form.content}}</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialong.show = false">取 消</el-button>
        <el-button v-if="form.status=='TO_AUDIT' || form.status=='NO_PASS'" type="primary" @click="updateStatus(form.id,'PASS')">通过</el-button>
        <el-button v-if="form.status=='TO_AUDIT' || form.status=='PASS'" type="primary" @click="updateStatus(form.id,'NO_PASS')">不通过</el-button>
        <el-button v-if="form.status=='PASS'" type="primary" @click="updateStatus(form.id,'LOSE_EFFICACY')">停效</el-button>
        <el-button v-if="form.status=='LOSE_EFFICACY'" type="primary" @click="updateStatus(form.id,'PASS')">启用</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: "VideoTeachingDialong",
  data() {
    return {

    };
  },
  props: {
    dialong: Object,
    form: {
         id: null,
         commentator: null,
         status: null,
         content: null
    },
    id: 0
  },
  methods: {
    updateStatus(id,status) {
            this.$axios.put("/api/evaluation/"+id+'/'+status, this.form).then(res => {
                if(res.data.status = 200) {
                  this.$message({
                    type: "success",
                    message: res.data.message
                  });
                   (this.dialong.show = false);
                   this.$emit("evaluationData");
                }else {
                 this.$message({
                    type: "error",
                    message: res.data.message
                  });
                }
            });
    }
  }
};
</script>
<style scoped>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
