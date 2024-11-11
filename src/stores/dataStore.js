// stores/dataStore.js
import { defineStore } from 'pinia';
import { analyseUrl, showUrl } from "@/api/project/project";
import { getPerson, getList } from "@/api/person/person";

export const useDataStore = defineStore('dataStore', {
    state: () => ({
        projectData: {}, // 存储 showUrl 数据
        personData: null, // 存储 getPerson 数据
        listData: {}, // 存储 getList 数据
        // singleData: null, // 存储 getSingle 数据
        loadingStates: {
            analyseUrl: false,
            getPerson: false,
            getList: false,
        },
    }),
    actions: {
        async fetchAndShowUrl(data) {
            this.loadingStates.analyseUrl = true;
            try {
                const analyseResponse = await analyseUrl(data);
                if (analyseResponse.data.code === 200) {
                    const showResponse = await showUrl(data);
                    this.projectData = showResponse.data; // 将 showUrl 数据存储到 state
                    return true;
                }
                return false;
            } catch (error) {
                console.error("Error fetching URL data:", error);
                return false;
            } finally {
                this.loadingStates.analyseUrl = false;
            }
        },
        async fetchPersonData() {
            this.loadingStates.getPerson = true;
            try {
                const personResponse = await getPerson();
                if (personResponse.data.code === 200) {
                    this.personData = personResponse.data; // 存储 getPerson 数据
                    return true;
                }
                return false;
            } catch (error) {
                console.error("Error fetching person data:", error);
                return false;
            } finally {
                this.loadingStates.getPerson = false;
            }
        },
        async fetchListData(data) {
            this.loadingStates.getList = true;
            try {
                const listResponse = await getList(data);
                this.listData = listResponse.data; // 存储 getList 数据
            } catch (error) {
                console.error("Error fetching list data:", error);
            } finally {
                this.loadingStates.getList = false;
            }
        },
        async fetchAllData(requestData) {
            // 按顺序调用
            const analyseSuccess = await this.fetchAndShowUrl(requestData);
            if (analyseSuccess) {
                console.log('getperson');

                const personSuccess = await this.fetchPersonData();
                if (personSuccess) {
                    console.log('getlist');

                    await this.fetchListData(requestData); // 获取 list 数据
                }
            }
        }
    },
});
