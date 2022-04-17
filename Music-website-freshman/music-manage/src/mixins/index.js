import th from 'element-ui/src/locale/lang/th'

export const mixin = {
  methods: {
    // 提示信息
    notify (title, type) {
      this.$notify({
        title: title,
        type: type
      })
    },
    getUrl (url) {
      return `${this.$store.state.HOST}${url}`
    },
    //  获取性别
    changeSex (value) {
      if (value === 0) {
        return '女'
      }
      if (value === 1) {
        return '男'
      }
      if (value === 2) {
        return '组合'
      }
      if (value === 3) {
        return '不明'
      }
      return value
    },
    //  获取生日值
    changeBirth (val) {
      return String(val).substr(0, 10)
    },
    //  上传图片之前的校验
    beforeAvatorUpload (file) {
      const isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png')
      if (!isJPG) {
        this.$message.error('上传头像图片只能是jpg或png格式')
        return false
      }
      // const isLt2M = (file.size / 1024 / 1024) < 2
      // if (isLt2M) {
      //   this.$message.error('上传头像图片大小不能超过2MB')
      //   return false
      // }
      return true
    },
    //  上传图片成功后的操作
    handleAvatorSuccess (res, file) {
      let _this = this
      if (res.code === 1) {
        _this.getData()
        _this.notify(
          '上传成功',
          'success'
        )
      } else {
        _this.notify(
          '上传失败',
          'error'
        )
      }
    },
    //  弹出删除窗口
    handleDelete (id) {
      this.idx = id
      this.delVisible = true
    },
    //  把已经选择的项赋值给handleSelectionChange
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    //   批量删除的项
    delAll () {
      for (let item of this.multipleSelection) {
        this.handleDelete(item.id)
        this.deleteRow()
      }
      this.multipleSelection = []
    }
  }
}
