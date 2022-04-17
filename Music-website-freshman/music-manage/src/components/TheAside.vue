<template>
  <div class="sidebar">
    <el-menu
      class="sidebar-el-menu"
      :default-active="onRoutes"
      :collapse="collapse"
      background-color="#324157"
      text-color="#ffffff"
      active-text-color="#20a0ff"
      unique-opened
      router
    >
      <template v-for="item in items">
          <template>
            <el-menu-item :index="item.index" :key="item.index">
              <i :class="item.icon"></i>
              <span slot="title">{{ item.title }}</span>
            </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
import bus from '../assets/js/bus'

export default {
  data () {
    return {
      //  菜单的折叠属性
      collapse: false,
      items: [
        {
          icon: 'el-icon-document',
          index: 'info',
          title: '系统首页'
        },
        {
          icon: 'el-icon-document',
          index: 'consumer',
          title: '用户管理'
        },
        {
          icon: 'el-icon-document',
          index: 'singer',
          title: '歌手管理'
        },
        {
          icon: 'el-icon-document',
          index: 'songList',
          title: '歌单管理'
        }
      ]
    }
  },
  computed: {
    onRoutes () {
      return this.$route.path.replace('/', '')
    }
  },
  created () {
    // 通过 Event Bus 进行组件间通信，来折叠侧边栏
    bus.$on('collapse', msg => {
      this.collapse = msg
    })
  }
}
</script>

<!--
style scoped实现组件的私有化，不对全局造成样式污染，表示当前style属性只属于当前模块-->

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  background-color: #334256;
  left: 0;
  top: 70px;
  bottom: 0;
  /*超出范围的话沿着y轴滚动*/
  overflow-y: scroll;
}

/*这里是把上下查找的侧边栏的宽度置为零*/
.sidebar::-webkit-scrollbar {
  width: 0;
}

/*意思是如果是折叠状态就不使用这个样式*/
.sidebar-el-menu:not(.el-menu--collapse) {
  width: 150px;
}
.sidebar > ul {
  height: 100%;
}
</style>
