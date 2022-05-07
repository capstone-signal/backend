# backend
backend for capstone-signal project


### Gradle Tasks
- `build` : build the project
- `jibDockerBuild` : build the project with jib ( Local Docker Image Build )
- `jib`: build the project with jib & push to ECR


### Required Environment Variables
- `EMAIL_USERNAME` : email username
- `EMAIL_PASSWORD` : email password for smtp server
- `GITHUB_CLIENT_ID` : github client id
- `GITHUB_CLIENT_SECRET` : github client secret
- `GITHUB_REDIRECT_URI` : github redirect uri
- `JWT_SECRET_KEY` : jwt secret key
- `HOME_URL`: home url (redirect after login)

### ERD
![entity relation](https://user-images.githubusercontent.com/43488326/163690749-8d74ecd2-ff62-448d-8ea8-4814ab2a9d54.png)
