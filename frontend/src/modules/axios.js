import { axios } from '@bundled-es-modules/axios'
axios.defaults.withCredentials = true;
export default function () {
    const BASE_URL = 'https://flask.giopaik.me/api/'
    const axiosGet = (URL, onSuccess=null, onFailed=null) => {
        const final_URL = URL.startsWith('http') ? URL : BASE_URL + URL
        var header = { "headers": {}}
        const insToken = localStorage.getItem("instructorLoginToken")
        const trToken = localStorage.getItem("traineeLoginToken")
        if (insToken) {
            header = { "headers": { "Authorization": insToken }}
        }
        if (trToken) {
            header = { "headers": { "Authorization": trToken }}
        }
        axios.get(final_URL, header).then((resp) => {
            if (resp.status === 200 || resp.data.rsp === 'ok' || resp.status === 201) {
                if (onSuccess) {
                    onSuccess(resp.data)
                } else {
                    if (onFailed) {
                        onFailed(resp.data)
                    }
                }
            }
        })
    }
    const axiosPost = (URL, data, onSuccess=null, onFailed=null) => {
        const final_URL = URL.startsWith('http') ? URL : BASE_URL + URL
        var header = { "headers": {}}
        const insToken = localStorage.getItem("instructorLoginToken")
        const trToken = localStorage.getItem("traineeLoginToken")
        if (insToken) {
            header = { "headers": { "Authorization": insToken }}
        }
        if (trToken) {
            header = { "headers": { "Authorization": trToken }}
        }
        axios.post(final_URL, data, header).then((resp) => {
            console.log("Axios POST Response Status" + resp.status)
            if (resp.status === 200 || resp.data.rsp === 'ok' || resp.status === 201) {
                if (onSuccess) {
                    onSuccess(resp.data)
                }
            } else {
                if (onFailed) {
                    onFailed(resp.data)
                }
            }
        }).catch(err =>{
            if (onFailed) {
                onFailed(err)
            }
        })
    }
    const axiosDelete = (URL, onSuccess=null, onFailed=null) => {
        const final_URL = URL.startsWith('http') ? URL : BASE_URL + URL
        var header = { "headers": {}}
        const insToken = localStorage.getItem("instructorLoginToken")
        const trToken = localStorage.getItem("traineeLoginToken")
        if (insToken) {
            header = { "headers": { "Authorization": insToken }}
        }
        if (trToken) {
            header = { "headers": { "Authorization": trToken }}
        }
        axios.delete(final_URL, header).then((resp) => {
            if (resp.status === 200 || resp.data.rsp === 'ok' || resp.status === 201) {
                if (onSuccess) {
                    onSuccess(resp.data)
                }
            } else {
                if (onFailed) {
                    onFailed(resp.data)
                }
            }
        }).catch(err =>{
            if (onFailed) {
                onFailed(err)
            }
        })
    }
    const axiosPut = (URL, data, onSuccess=null, onFailed=null) => {
        const final_URL = URL.startsWith('http') ? URL : BASE_URL + URL
        var header = { "headers": {}}
        const insToken = localStorage.getItem("instructorLoginToken")
        const trToken = localStorage.getItem("traineeLoginToken")
        if (insToken) {
            header = { "headers": { "Authorization": insToken }}
        }
        if (trToken) {
            header = { "headers": { "Authorization": trToken }}
        }
        axios.put(final_URL, data, header).then((resp) => {
            if (resp.status === 200 || resp.data.rsp === 'ok' || resp.status === 201) {
                if (onSuccess) {
                    onSuccess(resp.data)
                }
            } else {
                if (onFailed) {
                    onFailed(resp.data)
                }
            }
        }).catch(err =>{
            if (onFailed) {
                onFailed(err)
            }
        })
    }
    return {
        axiosGet,
        axiosPost,
        axiosDelete,
        axiosPut,
    }
}