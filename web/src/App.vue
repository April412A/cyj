<!--<template>
  <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </div>
  <router-view/>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>-->

<template>
  <a-layout id="components-layout-demo-top-side-2">
    <the-header></the-header>
    <router-view/>
    <the-footer></the-footer>
  </a-layout>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import TheHeader from '@/components/the-header.vue';
import TheFooter from '@/components/the-footer.vue';

export default defineComponent({
  name: 'app',
  components: {
    TheHeader,
    TheFooter,
  },
});
</script>

<style>
#components-layout-demo-top-side-2 .logo {
  width: 120px;
  height: 31px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px 28px 16px 0;
  float: left;
}
</style>


