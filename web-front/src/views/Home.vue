<template>
  <el-container>
    <el-aside width="160px"><application-tree></application-tree></el-aside>
    <el-main><router-view class="router-content"></router-view></el-main>
  </el-container>
</template>

<script>
// @ is an alias to /src
import ApplicationTree from '@/components/ApplicationTree.vue'

export default {
  name: 'home',
  components: {
    ApplicationTree
  }
}
</script>
