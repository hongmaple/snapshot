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
      <el-form :model="form" ref="formdoalog" :rules="formdialog" label-width="80px">
        <el-form-item label="标题" prop="title">
          <span>{{form.title}}</span>
        </el-form-item>
         <el-form-item label="状态" prop="title">
          <span style="margin-left: 10px">{{ form.status=='TO_AUDIT'? '待审核':(form.status=='PASS'?'通过':(form.status=='NO_PASS'?'不通过':'失效')) }}</span>
        </el-form-item>
        <el-form-item label="文件类型" prop="title">
          <span style="margin-left: 10px">{{ form.type==1? '图片':'视频' }}</span>
        </el-form-item>
         <el-form-item label="作品类型" prop="title">
          <span style="margin-left: 10px">{{ form.workType==1? '文明点赞':'爆光台' }}</span>
        </el-form-item>
        <el-form-item label="文件" prop="void">
           <!-- <el-upload
                class="avatar-uploader"
                action="/api/common/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccessVoid"
                :before-upload="beforeAvatarUploadVoid"
                 v-model="form.pic"
                >
                 <video width="320" v-if="form.url" height="240" controls="controls">
                        <source :src="'api/'+form.url" type="video/mp4">
                 </video>
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload> -->
            <div v-if="form.type==1">
              <div v-for="(item,i) in form.url" :key="i">
                  <img style="float: left;" alt="图片" v-if="item" :src="'api'+item" class="avatar">
              </div>
            </div>
            <div v-if="form.type==2">
               <div v-for="(item,i) in form.url" :key="i">
                   <video width="320" v-if="item" height="240" controls="controls">
                        <source :src="'api'+item" type="video/mp4">
                   </video>
              </div>
            </div>
            
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
      formdialog: {
        name: [{ required: true, message: "用户名不能为空", trigger: "blur" }],
        phone: [{ required: true, message: "手机号不能为空", trigger: "blur" }]
      }
    };
  },
  props: {
    dialong: Object,
    form: {
         id: null,
         title: null,
         url: null,
         type: null,
         status: null
    },
    id: 0
  },
  methods: {
    addHandle(formdoalog) {
      this.$refs[formdoalog].validate(valid => {
        if (valid) {
          if(this.dialong.option == "add") {
            this.$axios.post("/api/work", this.form).then(res => {
                this.$message({
                  type: "success",
                  message: "数据添加成功"
                });
                (this.dialong.show = false);
                this.$emit("UserData");
                //清空内容
                this.form = "";
            });
          }else {
            const formData = this.form;
            formData.id = this.id;
            this.$axios.put("/api/work", formData,{headers: {"token": localStorage.getItem("eleToken")}}).then(res => {
                this.$message({
                  type: "success",
                  message: "数据修改成功"
                });
                (this.dialong.show = false);
                this.$emit("UserData");
                //清空内容
                this.form = "";
            });
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },   
    updateStatus(id,status) {
            this.$axios.put("/api/work/"+id+'/'+status, this.form).then(res => {
                if(res.data.status = 200) {
                  this.$message({
                    type: "success",
                    message: res.data.message
                  });
                   (this.dialong.show = false);
                   this.$emit("UserData");
                }else {
                 this.$message({
                    type: "error",
                    message: res.data.message
                  });
                }
            });
    } ,
    handleAvatarSuccess(res, file) {
            //URL.createObjectURL(file.raw);
            this.form.pic = res.fileName;
    },
    beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/png' ||  file.type === 'image/svg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
            this.$message.error('上传头像图片只能是 jpeg，JPG，png，svg格式!');
            }
            if (!isLt2M) {
            this.$message.error('上传图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
    },
    handleAvatarSuccessVoid(res, file) {
            //URL.createObjectURL(file.raw);
            this.form.url = res.fileName;
    },
    beforeAvatarUploadVoid(file) {
            const isJPG = file.type === 'video/mp4';
            const isLt100000M = file.size / 1024 / 1024 < 100000;

            if (!isJPG) {
            this.$message.error('上传视频只能是mp4格式!');
            }
            if (!isLt100000M) {
            this.$message.error('上传图片大小不能超过 100000MB!');
            }
            return isJPG && isLt100000M;
    },
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
