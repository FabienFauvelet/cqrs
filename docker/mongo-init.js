db.auth('root','root');

db.createUser(
        {
                user: 'albert',
                pwd: 'einstein',
                roles: [
                        { role: 'readWrite', db: 'admin'},
                        { role: 'readWrite', db: 'courses'},
                        { role: 'readWrite', db: 'references'}
                        ]
        }
);

db.createUser(
{
    user:'front',
    pwd:'front',
    roles:
        [{role: 'read', db: 'courses'}]
});