<template>
  <div id="app">
    <h2 v-html="msg"></h2>
    <input type="text" v-model="itemNew" @keyup.enter="addNew" />
    <ul>
      <li v-for="item in items" :key="item.name" :class="{ isMeal: item.meal }" @click="turnRed(item)">
        {{ item.name }}
      </li>
    </ul>
  </div>
</template>

<script>
import Storage from './localstorage'

export default {
  name: 'App',

  data() {
    return {
      msg: '请输入需要点的菜',
      items: Storage.fetch(),
      itemNew: ''
    }
  },

  methods: {
    turnRed(item) {
      item.meal = !item.meal;
    },

    addNew() {
      if (this.itemNew.trim() !== '') {
        this.items.push({
          name: this.itemNew,
          student: false
        });
        this.itemNew = ''; 
      }
    }
  },

  watch: {
    items: {
      handler(items) {
        Storage.save(items);
      },
      deep: true
    }
  }
}
</script>

<style>
.isMeal {
  color: red;
}

.centered-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>