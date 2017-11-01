# TypeAHead

TypeAHead is a project created with the goal to demonstrate a typeahead
using the SeatGeek API.

## Libraries Used
- Dagger2
- Retrofit
- Picasso
- Firebase Database
- Autovaule (parcel, gson extensions)
- Butterknife
    
## Things to improve
- Would like to create a more inclusive DB, maybe with Firebase or a traditional
      sqlite implementation
- Visually, I'd like to add a layered view for multiple performer icons. I know
      it's indicated that we can have multiple performers in the title but something
      on the images would be interesting
- Add Espresso tests 
- User credentials on the database so that each user would have their own
      personalized data store
- An activity that would display just a list of favorites
- Keystore to store client keys
- Some sort of interface to abstract the implementation of Events