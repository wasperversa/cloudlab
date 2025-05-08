#include<iostream>
#include<string.h>
using namespace std;
int main(){
    char pass[20],epass[20];
    int numone,numtwo,sum;
    cout<<"create a password:";
    cin>>pass;
    cout<<"\nEnter two numbers to add:";
    cin>>numone>>numtwo;
    cout<<"\nEnter password to see the result:";
    cin>>epass;
    if(!strcmp(pass, epass)){
        sum=numone+numtwo;
        cout<<endl<<numone<<"+"<<numtwo<<"="<<sum;

    }else
    cout<<"You've entered Wrong Password!";
    cout<<endl;
    return 0;
}