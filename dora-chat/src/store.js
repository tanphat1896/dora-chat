import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    name: null,
    avt: null
  },
  mutations: {
    SET_LOGIN(state, payload) {
      state.name = payload.name || null;
      state.avt = payload.avt || null;
    }
  },
  actions: {
    actionJoin({ commit }, payload) {
      console.log("stor1");
      commit("SET_LOGIN", payload);
      console.log("stor2");
    }
  }
});
