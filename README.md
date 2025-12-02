Para realizar o deploy dos servicos é necessario ter o AWS CLI instalado e configurado. 
Siga os passos abaixo para instalar e configurar o AWS CLI:

sudo apt update
sudo apt install -y unzip curl
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o awscliv2.zip
unzip awscliv2.zip && sudo ./aws/install
aws --version

aws configure 
AWS Access Key ID: <KEY>
AWS Secret Access Key: <SECRET>
Default region name: sa-east-1
Default output format: json

Apos configurar o AWS CLI, voce podera utilizar o script deploy-lambda.sh para realizar o deploy na AWS.

Para rodar o shell script é necessário estar na pasta do projeto usando o wsl e executar um mvn clean package
após isso é possível publicar ou atualizar a lambda

exemplo: ./deploy-lambda.sh create ou ./deploy-lambda.sh update
É necessario que o comando seja executado a partir do linux ou wsl.