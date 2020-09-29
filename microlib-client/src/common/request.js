import axios from 'axios'

const BASE_URL = 'http://localhost:8081'

const request = axios.create({
    baseURL: BASE_URL,
    timeout: 5000
})

request.adornUrl = (requestUrl) => `${BASE_URL+requestUrl}`
request.adornErrorMsg = function (e) {
    if (!e.response) {
        return e
    }
    return e.response.data.message
}

export default request