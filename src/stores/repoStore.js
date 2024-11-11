// src/stores/repoStore.js
import { defineStore } from 'pinia'

export const useRepoStore = defineStore('repo', {
    state: () => ({
        repoUrl: ''
    }),
    actions: {
        setRepoUrl(url) {
            this.repoUrl = url
        }
    }
})
