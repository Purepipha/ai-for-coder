declare namespace API {
  type DeleteRequest = {
    id?: number
  }

  type getUserByIdParams = {
    id: number
  }

  type getUserVOByIdParams = {
    id: number
  }

  type LoginUserVo = {
    id?: number
    userAccount?: string
    userName?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
    createTime?: string
    updateTime?: string
  }

  type PageUserVo = {
    records?: UserVo[]
    pageNumber?: number
    pageSize?: number
    totalPage?: number
    totalRow?: number
    optimizeCountQuery?: boolean
  }

  type ResultBoolean = {
    code?: number
    data?: boolean
    message?: string
    description?: string
  }

  type ResultLoginUserVo = {
    code?: number
    data?: LoginUserVo
    message?: string
    description?: string
  }

  type ResultLong = {
    code?: number
    data?: number
    message?: string
    description?: string
  }

  type ResultPageUserVo = {
    code?: number
    data?: PageUserVo
    message?: string
    description?: string
  }

  type ResultString = {
    code?: number
    data?: string
    message?: string
    description?: string
  }

  type ResultUser = {
    code?: number
    data?: User
    message?: string
    description?: string
  }

  type ResultUserVo = {
    code?: number
    data?: UserVo
    message?: string
    description?: string
  }

  type User = {
    id?: number
    userAccount?: string
    userPassword?: string
    userName?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
    editTime?: string
    createTime?: string
    updateTime?: string
    isDelete?: number
  }

  type UserAddRequest = {
    userName?: string
    userAccount?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
  }

  type UserLoginRequest = {
    userAccount?: string
    userPassword?: string
  }

  type UserQueryRequest = {
    pageNum?: number
    pageSize?: number
    sortField?: string
    sortOrder?: string
    id?: number
    userName?: string
    userAccount?: string
    userProfile?: string
    userRole?: string
  }

  type UserRegisterRequest = {
    username?: string
    password?: string
    checkPassword?: string
  }

  type UserUpdateRequest = {
    userName?: string
    userAccount?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
    id?: number
  }

  type UserVo = {
    id?: number
    userAccount?: string
    userName?: string
    userAvatar?: string
    userProfile?: string
    userRole?: string
    createTime?: string
    editTime?: string
    updateTime?: string
  }
}
