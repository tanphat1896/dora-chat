<template>
  <div>
    <div class="login-bg"></div>
    <div class="ui container">
      <div class="ui compact segment">
        <h3 class="center aligned">Trò chuyện cùng Doraemon nhé</h3>
        <div class="fields" :class="{ error: error.username || error.password }">
          <div class="six wide field">
            <label>Tài khoản:</label>
            <input type="text" v-model="username" />
          </div>
          <div class="six wide field" :class="{ error: error.password }">
            <label>Mật khẩu:</label>
            <input type="password" v-model="password" />
          </div>
          <button class="ui button" @click="join">Login</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      error: {
        username: false,
        password: false
      },
      username: "nphat",
      password: "nphat"
    };
  },
  methods: {
    ...mapActions(["actionJoin"]),
    async join() {
      if (!this.username || !this.password) {
        this.error = {
          name: !this.username,
          avt: !this.password
        };
        return;
      }
      await this.actionJoin({
        username: this.username,
        password: this.password
      });
      this.$router.push({ name: "Main" });
    }
  }
};
</script>

<style lang="scss" scoped>
@import '@/assets/scss/login.scss';
</style>
