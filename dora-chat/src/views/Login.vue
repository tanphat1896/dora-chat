<template>
  <div>
    <div class="login-bg"></div>
    <div class="ui container">
      <div class="ui compact segment">
        <h3 class="center aligned">Tham gia với Doraemon nhé</h3>
        <strong>Chọn một ảnh nào</strong>
        <div class="avt--choosing" :class="{ error: error.avt }">
          <img
            v-for="(name, i) in avtName"
            :key="i"
            class="avt"
            :src="require(`@/assets/img/${name}.png`)"
            :class="{ active: name === avt }"
            @click="
              avt = name;
              error.avt = false;
            "
            :title="name"
          />
        </div>
        <div class="ui action fluid input" :class="{ error: error.name }">
          <input
            @focus="error.name = false"
            type="text"
            placeholder="Nhập tên của bạn..."
            v-model="name"
            @keyup.enter="join"
          />
          <button class="ui icon blue button" @click="join">
            <i class="sign in alternate icon"></i>
          </button>
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
        name: false,
        avt: false
      },
      name: "",
      avt: null,
      avtName: [
        "dora01",
        "dorami",
        "nobita",
        "shizuka",
        "suneo",
        "jaian",
        "nobisuke",
        "tamako",
        "dora-sorry",
        "dora-joy",
        "teacher"
      ]
    };
  },
  methods: {
    ...mapActions(["actionJoin"]),
    join() {
      if (!this.name || !this.avt) {
        this.error = {
          name: !this.name,
          avt: !this.avt
        };
        return;
      }
      this.actionJoin({
        name: this.name,
        avt: require(`@/assets/img/${this.avt}.png`)
      });
      this.$router.push({ name: "Main" });
    }
  }
};
</script>

<style lang="scss" scoped>
.login-bg {
  background-image: url("~@/assets/img/login-bg.jpg");
  background-size: 100% auto;
  background-position: top;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
.segment {
  position: fixed;
  top: 50%;
  transform: translate(-75%, -60%);
  position: fixed;
  left: 50%;
}
.avt {
  &--choosing {
    margin: 5px 0;
    &.error {
      box-shadow: 0 0 5px red;
    }
  }
  cursor: pointer;
  width: 48px;
  border: 1px dashed gray;
  border-radius: 999px;
  padding: 5px;
  &:not(:last-child) {
    margin-right: 5px;
  }
  &:hover {
    border: none;
    box-shadow: 0 0 5px lightblue;
  }
  &.active {
    border: 3px solid green;
  }
}
</style>
