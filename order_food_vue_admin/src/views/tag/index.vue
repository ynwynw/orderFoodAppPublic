<template>
  <el-card class="main-card">
    <div class="title">{{ this.$route.meta.menuName }}</div>
    <!-- 表格操作 -->
    <div class="operation-container" style="margin-top: 24px">
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="openModelAdd(null)"
      >
        新增
      </el-button>
      <el-button
        type="danger"
        size="small"
        icon="el-icon-delete"
        :disabled="tagIdList.length == 0"
        @click="isDelete = true"
      >
        批量删除
      </el-button>

    </div>
    <!-- 表格展示 -->
    <el-table

      row-key="id"
      :default-expand-all="isExpandAll"
      :data="tagList"
      v-loading="loading"
      @selection-change="selectionChange"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"

    >
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" />
      <!-- 标签名 -->
      <el-table-column prop="name" label="标签名" align="center"/>
      <el-table-column prop="price" label="附加价格" align="center" />
      <!-- 排序 -->
      <el-table-column prop="sort" label="排序" align="center" />
      <el-table-column prop="status" label="禁用" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
              v-model="scope.row.status"
              active-color="#13ce66"
              inactive-color="#F4F4F5"
              :active-value="1"
              :inactive-value="0"
              @change="changeDisable(scope.row)"
          />
        </template>
      </el-table-column>
      <!-- 列操作 -->
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="openModelAdd(scope.row)">
            添加
          </el-button>
          <el-button type="text" size="mini" @click="openModel(scope.row)">
            编辑
          </el-button>
          <el-popconfirm
            title="确定删除吗？"
            style="margin-left:1rem"
            @confirm="deleteTag(scope.row.id)"
          >
            <el-button size="mini" type="text" slot="reference">
              删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 批量删除对话框 -->
    <el-dialog :visible.sync="isDelete" width="30%">
      <div class="dialog-title-container" slot="title">
        <i class="el-icon-warning" style="color:#ff9900" />提示
      </div>
      <div style="font-size:1rem">是否删除选中项？</div>
      <div slot="footer">
        <el-button @click="isDelete = false">取 消</el-button>
        <el-button type="primary" @click="deleteTag(null)">
          确 定
        </el-button>
      </div>
    </el-dialog>
    <!-- 编辑对话框 -->
    <el-dialog :visible.sync="addOrEdit" width="30%">
      <div class="dialog-title-container" slot="title" ref="tagTitle" />
      <el-form label-width="80px" size="medium" :model="tagForm">
        <el-form-item label="标签名">
          <el-input style="width:220px" v-model="tagForm.name" />
        </el-form-item>
        <el-form-item label="附加价格">
          <el-input style="width:220px" v-model="tagForm.price" />

        </el-form-item>
       <el-form-item label="排序">
         <el-input-number v-model="tagForm.sort" controls-position="right" :min="0" />
       </el-form-item>
        <el-table-column prop="orderNum" label="排序" width="60"></el-table-column>
      </el-form>
      <div slot="footer">
        <el-button @click="addOrEdit = false">取 消</el-button>
        <el-button type="primary" @click="addOrEditTag">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import {deleteTagList, getTagPage, saveOrUpdate} from "@/api/tag";

const {TagDisable} = require("@/api/tag");

export default {
  name:"MyTag",
  created() {
    this.listTags();
  },
  data: function() {
    return {
      isDelete: false,
      loading: true,
      addOrEdit: false,
      keywords: null,
      tagList: [],
      isExpandAll:false,
      tagIdList: [],
      tagForm: {
        id: null,
        name: "",
        sort:1,
        parentId:0,
      },
      current: 1,
      size: 10,
      count: 0
    };
  },
  methods: {
    selectionChange(tagList) {
      this.tagIdList = [];
      tagList.forEach(item => {
        this.tagIdList.push(item.id);
      });
    },
    async changeDisable(row) {
      const params = {
        id: row.id,
        isDisable: row.status
      }
      const res = await TagDisable(params);
      this.$notify.success({
        title: "成功",
        message: res.msg
      })
      await this.listTags();

    },
    searchTags() {
      this.current = 1;
      this.listTags();
    },
    sizeChange(size) {
      this.size = size;
      this.listTags();
    },
    currentChange(current) {
      this.current = current;
      this.listTags();
    },
    async deleteTag(id) {
      let param = [];
      if (id == null) {
        param = this.tagIdList
      } else {
        param =[id]
      }

      const res = await deleteTagList(param);
      if (res.code) {
        this.$notify.success({
          title: "成功",
          message: res.msg
        });
        await this.listTags();
      } else {
        this.$notify.error({
          title: "失败",
          message: res.msg
        });
      }
      this.isDelete = false;
    },
    async listTags() {
      const res = await getTagPage();
      console.log(res.data)
      this.tagList = res.data;
      this.loading = false;

    },
    openModelAdd(tag){

      this.tagForm.id=""
      this.tagForm.name=""
      this.tagForm.sort=1

      if(tag==null)
          this.tagForm.parentId = 0;
      else
         this.tagForm.parentId=tag.id;
      this.$refs.tagTitle.innerHTML = "添加标签";
      this.addOrEdit = true;

    },
    openModel(tag) {
      this.tagForm = JSON.parse(JSON.stringify(tag));
      this.$refs.tagTitle.innerHTML = "修改标签";
      this.addOrEdit = true;
    },
    async addOrEditTag() {
      if (this.tagForm.name.trim() == "") {
        this.$message.error("标签名不能为空");
        return false;
      }
      const res = await saveOrUpdate(this.tagForm);
      if (res.code) {
        this.$notify.success({
          title: "成功",
          message: res.msg
        });
       await this.listTags();
      } else {
        this.$notify.error({
          title: "失败",
          message: res.msg
        });
      }
      this.addOrEdit = false;
    }
  }
};
</script>
