<template>
  <div class="the-footer">
    <p v-for="(item, index) in list" :key="index">
      {{item}}
    </p>
  </div>
</template>

<script>
export default {
  name: 'the-footer',
  data () {
    return {
      list: [
        `如听仙乐耳暂明`,
        `Copyright © 2022`,
        `Designed by Li-MengYang`
      ]
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/the-footer.scss';
</style>
