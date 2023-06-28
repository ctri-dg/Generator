#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main(){
    if(fork()){
        if(fork()){
            execlp("java", "java", "-jar", "api-gateway.jar", NULL);
        }else{
            execlp("java", "java", "-jar", "data-provider.jar", NULL);
        }
    }else{
        execlp("java", "java", "-jar", "naming-server.jar", NULL);
    }
}