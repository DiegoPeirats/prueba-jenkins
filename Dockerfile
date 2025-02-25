# Usa una imagen base de Windows
FROM mcr.microsoft.com/windows/servercore:ltsc2019

# Cambiar al usuario administrador
USER Administrator

# Descargar e instalar Docker en Windows
RUN powershell -Command ` 
    Invoke-WebRequest -UseBasicParsing -Uri https://download.docker.com/win/static/stable/x86_64/docker-20.10.8.zip -OutFile docker.zip; `
    Expand-Archive -Path docker.zip -DestinationPath C:\Docker; `
    Remove-Item -Force docker.zip


# Agregar Docker a la variable de entorno
ENV PATH="C:\Docker;$PATH"

# Descargar e instalar Maven en Windows
ARG MAVEN_VERSION=3.9.8
RUN powershell -Command `
    Invoke-WebRequest -UseBasicParsing -Uri https://downloads.apache.org/maven/maven-3/$env:MAVEN_VERSION/binaries/apache-maven-$env:MAVEN_VERSION-bin.zip -OutFile maven.zip; `
    Expand-Archive -Path maven.zip -DestinationPath C:\Tools; `
    Rename-Item -Path C:\Tools\apache-maven-$env:MAVEN_VERSION -NewName C:\Tools\maven; `
    Remove-Item -Force maven.zip

# Configurar variable de entorno para Maven
ENV MAVEN_HOME="C:\Tools\maven"
ENV PATH="C:\Tools\maven\bin;$PATH"

# Volver al usuario Jenkins
USER jenkins
