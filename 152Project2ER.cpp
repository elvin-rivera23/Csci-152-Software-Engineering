/*
  Elvin Rivera
  Csci 152 Spring 2017
  Project 2

  Composite Design of Fresno State hierarchy

  - to compile in cygwin: g++ 152Project2ER.cpp -o "name of executable"
  - to run in cygwin: ./"name of executable"

*/

#include <cstdio>
#include <cstring>
#include <iostream>
#include <vector>

using namespace std;

// ---- begin Component class -----
class Component
{
  public:
	Component(std::string name, std::string major)
		  : m_fullName(name), m_valueMajor (major) {}

    virtual void printMajor(int level) = 0;

    std::string  m_fullName;
    std::string  m_valueMajor;
};
// ---- end Component class

// ----begin Student class
class Student : public Component
{
  public:
    Student(std::string name , std::string major): Component(name,major)
	{
    }
    // print students major
    void printMajor(int level)
    {
		for(int j=0; j < level; ++j) cout << "\t";
        cout << "Student : " <<  m_fullName.c_str() << " , Major: " << m_valueMajor << "\n";
    }

};
// ----end student class

// ----begin school class
class School: public Component
{
  public:

    School(std::string name , std::string major) : Component(name,major)
    {
    }

    void add(Component *cmp)
    {
        m_children.push_back(cmp);
    }

    //print school
    void printMajor(int level)
    {
		    for(int j=0; j < level; ++j) cout << "\t";
        cout << "School : " <<  this->m_fullName.c_str() << " " << m_valueMajor << "\n";

		      if(!m_children.empty())
		        {
              // show what is a Subordinate of departmant
			           for(int x=0; x < level; ++x) cout << "\t";
			             cout << "Subordinates of " <<  m_fullName.c_str() << ":\n";
			             ++level;
			               for (int i = 0; i < m_children.size(); ++i)
			                  m_children[i]->printMajor(level);
            }

    }

  private:
	   vector < Component * > m_children;
};
// ---- end school class

// ----- Begin main -----
int main()
{
	//Declare the campus
	School mainSchool ("California State University Fresno", "All Majors");

	//define what colleges/schools there are at fresno state
  School schoolCSM ("CSM", "");
	School schoolLyles ("Lyles College of Engineering", "");
  School schoolCraig ("Craig School of Business", "");

	//define students of several departments
	School studentOfCsci ("Computer Science", "");
	School studentOfBio ("Biology", "");
  School studentOfECE ("Electrical and Computer Engineering", "");
  School studentOfBusiness ("Business", "");

	//defining students of each major

  //computer science students
	Student CsciStudent1 ("Ken", "Computer Science");
	Student CsciStudent2 ("Alex", "Computer Science");
  Student CsciStudent3 ("Jonathan", "Computer Science");
  Student CsciStudent4 ("Jose", "Computer Science");
  Student CsciStudent5 ("Victoria", "Computer Science");
  Student CsciStudent6 ("Omar", "Computer Science");

  //biology studdents
  Student BioStudent1 ("Jason", "Biology");
  Student BioStudent2 ("Ray", "Biology");

  //electrical and computer engineer students
  Student ECEStudent1 ("Rahal", "Electrical Engineering");
  Student ECEStudent2 ("Conner", "Computer Engineering");

  //electrical and computer engineer students
  Student BusinessStudent1 ("Abel", "Economics");
  Student BusinessStudent2 ("Sal", "Accounting");
  Student BusinessStudent3 ("Layna", "Management");

  // ------- College of Math and Sciences ---------
  // add college of math and science and students
	mainSchool.add(&schoolCSM);

    schoolCSM.add(&studentOfCsci);
      // add number of students to computer science major
      studentOfCsci.add(&CsciStudent1);
      studentOfCsci.add(&CsciStudent2);
      studentOfCsci.add(&CsciStudent3);
      studentOfCsci.add(&CsciStudent4);
      studentOfCsci.add(&CsciStudent5);
      studentOfCsci.add(&CsciStudent6);

    schoolCSM.add(&studentOfBio);
     // add number of students to biology major
	   studentOfBio.add(&BioStudent1);
     studentOfBio.add(&BioStudent2);

  //-------- Lyles College of Engineering------
  //add Lyles College and students
	mainSchool.add(&schoolLyles);

    schoolLyles.add(&studentOfECE);
    //  add number of students to electrical and computer engineering major
    studentOfECE.add(&ECEStudent1);
    studentOfECE.add(&ECEStudent2);

  // --------- Craig School of Business ---------
  // add Craig college and students
  mainSchool.add(&schoolCraig);

    schoolCraig.add(&studentOfBusiness);
    // add number of students to Business
    studentOfBusiness.add(&BusinessStudent1);
    studentOfBusiness.add(&BusinessStudent2);
    studentOfBusiness.add(&BusinessStudent3);


  // hierarchy introduction
	cout << "The hierarchy of the school,\ni.e. Fresno State and its students :\n\n\n" ;
	mainSchool.printMajor(0);

    cout << '\n';
}
