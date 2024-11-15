# DevGauge

## 项目简介

DevGauge 是一个基于 Spring Boot 的应用程序，旨在分析 GitHub 开源项目的开发者贡献和项目重要性。通过提供用户友好的界面和强大的数据分析功能，DevGauge 帮助用户更好地理解和评估 GitHub 上的开源项目。

## 功能特性

- **用户认证**: 支持 GitHub OAuth 登录，确保用户的安全性和隐私。
- **数据分析**: 提供开发者在特定项目中的贡献度分析，帮助评估其影响力。
- **用户分析**: 支持用户输入Github用户名分析用户
- **项目评价**: 根据开发者的贡献和项目的活跃度对项目进行评分，便于用户做出明智的选择。
- **用户界面**: 直观的用户界面，使得用户可以方便地查看和管理数据。
- **监控界面**: 更加直观的监控界面，可以查看接口调用次数，同时监控Github Api 是否正常。

## 技术栈

- **后端**: Java, Spring Boot, MyBatis, WebSocket, Spring Task
- **数据库**: MySQL
- **前端**: Vue.js
- **工具**: Druid (数据库连接池), Redis (缓存)

## 安装与配置

1. **克隆项目**:

   ```
   git clone https://github.com/zed1623/DevGauge.git
   ```

2. **数据库配置**:

   - 修改 `application.yml` 文件，确保数据库连接信息正确无误。
   - 请单独创建 `application-secret.yml`文件保存mysql账号及密码、Github、Ai密钥

3. **依赖管理**:

   - 使用 Maven 构建项目:

   ```
   mvn clean install
   ```

4. **运行应用**:

   - 使用以下命令启动 Spring Boot 应用：

   ```
   mvn spring-boot:run
   ```

## 使用说明

- 登录后，用户可以通过主界面查看开源项目的详细信息和分析结果。
- 用户可以选择不同的 GitHub 项目进行深入分析。
- 用户也可以使用用户名对用户进行分析评分。

## 贡献

欢迎提交问题或请求功能改进。请在 GitHub 上创建 issue 或 pull request。

作者邮箱：2710581308@qq.com

## 许可证

该项目遵循 [MIT 许可认证](https://opensource.org/licenses/MIT) 。