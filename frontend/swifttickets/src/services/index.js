import http from './axios'

const fetchLogin = async (body) => {
  const { data } = await http({
    method: 'POST',
    url: '/api/userlogin/login',
    data: body
  })
  http.defaults.headers.common['Authorization'] = data.data?.accessToken
  return data
}

const fetchRegister = async (body) => {
  const { data } = await http({
    method: 'POST',
    url: '/api/user-service/register',
    data: body
  })
  return data
}

const fetchTicketSearch = async (params) => {
  const { data } = await http({
    method: 'GET',
    url: '/api/ticket-service/ticket/query',
    params
  })
  return data
}

const fetchRegionStation = async (params) => {
  const { data } = await http({
    method: 'GET',
    url: '/api/ticket-service/region-station/query',
    params
  })
  return data
}

const fetchPassengerList = async (params) => {
  const { data } = await http({
    method: 'GET',
    url: '/api/user-service/passenger/query',
    params
  })
  return data
}
const fetchDeletePassenger = async (body) => {
  const { data } = await http({
    method: 'POST',
    url: '/api/user-service/passenger/remove',
    data: body
  })
  return data
}

const fetchAddPassenger = async (body) => {
  const { data } = await http({
    method: 'POST',
    url: '/api/user-service/passenger/save',
    data: body
  })
  return data
}

const fetchEditPassenger = async (body) => {
  const { data } = await http({
    method: 'POST',
    url: '/api/user-service/passenger/update',
    data: body
  })
  return data
}
const fetchLogout = async (token) => {
  const { data } = await http({
    method: 'GET',
    url: '/api/userlogin/logout?token=' + token,
  })
  http.defaults.headers.common['Authorization'] = null
  return data
}

const fetchBuyTicket = async (body) => {
  const { data } = await http({
    method: 'POST',
    url: '/api/ticket-service/ticket/purchase/v2',
    data: body
  })

  return data
}

const fetchOrderBySn = async (params) => {
  const { data } = await http({
    method: 'GET',
    url: '/api/order-service/order/ticket/query',
    params
  })
  return data
}

const fetchPay = async (body) => {
  const { data } = await http({
    method: 'POST',
    url: '/api/pay-service/pay/create',
    data: body
  })
  return data
}

const fetchStationAll = async () => {
  const { data } = await http({
    method: 'GET',
    url: '/api/ticket-service/station/all'
  })
  return data
}

const fetchUserInfo = async (params) => {
  const { data } = await http({
    method: 'GET',
    url: '/api/userlogin/info',
    params
  })
  return data
}

const fetchTrainStation = async (params) => {
  const { data } = await http({
    method: 'GET',
    url: '/api/ticket-service/train-station/query',
    params
  })
  return data
}

const fetchTicketList = async (params) => {
  const { data } = await http({
    method: 'GET',
    url: '/api/order-service/order/ticket/page',
    params
  })
  return data
}

const fetchOrderCancel = async (body) => {
  const { data } = await http({
    method: 'POST',
    url: '/api/ticket-service/ticket/cancel',
    data: body
  })
  return data
}

const fetchOrderStatus = async (params) => {
  const { data } = await http({
    method: 'GET',
    url: '/api/pay-service/pay/query/order-sn',
    params
  })
  return data
}

const fetchMyTicket = async (params) => {
  const { data } = await http({
    method: 'GET',
    url: '/api/order-service/order/ticket/self/page',
    params
  })
  return data
}

const fetchRefundTicket = async (body) => {
  const { data } = await http({
    method: 'POST',
    url: '/api/ticket-service/ticket/refund',
    data: body
  })
}

const fetchSendCode = async (phone) => {
  const { data } = await http({
    method: 'GET',
    url: '/api/userlogin/sendcode?phone=' + phone,
  })
}

export {
  fetchLogin,
  fetchRegister,
  fetchTicketSearch,
  fetchRegionStation,
  fetchPassengerList,
  fetchDeletePassenger,
  fetchAddPassenger,
  fetchEditPassenger,
  fetchLogout,
  fetchBuyTicket,
  fetchOrderBySn,
  fetchPay,
  fetchStationAll,
  fetchUserInfo,
  fetchTrainStation,
  fetchTicketList,
  fetchOrderCancel,
  fetchOrderStatus,
  fetchMyTicket,
  fetchRefundTicket,
  fetchSendCode
}
