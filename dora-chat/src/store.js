import Vue from "vue";
import Vuex from "vuex";
import * as auth from "./services/auth"

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: null,
    name: null,
    avt: null
  },
  mutations: {
    SET_LOGIN(state, payload) {
      state.name = payload.name || null;
      state.avt = payload.avt || null;
    },
    SET_TOKEN(state, token) {
      state.token = token;
    }
  },
  actions: {
    async actionJoin({ commit }, credential) {
      const { token } = await auth.signin(credential);
      commit("SET_TOKEN", token);
    }
  }
});
