db.auth('root','root');

db.createUser(
        {
                user: 'albert',
                pwd: 'einstein',
                roles: [
                        { role: 'readWrite', db: 'admin'},
                        { role: 'readWrite', db: 'courses'},
                        { role: 'readWrite', db: 'references'},
                        { role: 'readWrite', db: 'resources'},
                        { role: 'readWrite', db: 'teachers'}
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